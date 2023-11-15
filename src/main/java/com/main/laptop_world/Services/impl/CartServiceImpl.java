package com.main.laptop_world.Services.impl;

import com.main.laptop_world.Entity.*;
import com.main.laptop_world.Repository.CartRepository;
import com.main.laptop_world.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {
    private CartRepository cartRepository;
    private ProductService productService;
    private UserService userService;
    private OrderService orderService;
    private OrderItemService orderItemService;

    @Autowired
    @Lazy
    public CartServiceImpl(
            CartRepository cartRepository,
            ProductService productService,
            UserService userService,
            OrderService orderService,
            OrderItemService orderItemService) {
        this.cartRepository = cartRepository;
        this.productService = productService;
        this.userService = userService;
        this.orderService = orderService;
        this.orderItemService = orderItemService;
    }

    @Override
    public void addToCart(Long userId, Long productId, int quantity) {
        Optional<Cart> cartItemOptional = cartRepository.findByUserIdAndProductsId(userId, productId);
        Products product = productService.getProductById(productId);
        int stockQuantity = product.getQuantity();
        if (cartItemOptional.isPresent()) {
            Cart cartItem = cartItemOptional.get();
            int updatedQuantity = cartItem.getQuantity() + quantity;
            if (updatedQuantity <= stockQuantity) {
                cartItem.setQuantity(updatedQuantity); // Cộng thêm quantity vào số lượng hiện tại
                updateTotalPrice(cartItem);
                cartRepository.save(cartItem);
                product.setQuantity(stockQuantity - quantity);
                productService.saveProduct(product);
            }else {
                if (stockQuantity > 0) {

                    // Giới hạn số lượng sản phẩm thêm vào giỏ hàng bằng stockQuantity
                    cartItem.setQuantity(stockQuantity + quantity);
                    updateTotalPrice(cartItem);
                    cartRepository.save(cartItem);

                    // Giảm số lượng sản phẩm trong kho còn lại
                    product.setQuantity(0);
                    product.setStatus(false);
                    productService.saveProduct(product);
                }
            }
        } else {
            User user = userService.findById(userId);
            if (user != null) {
                Cart cartItem = new Cart();
                cartItem.setUser(user);
                cartItem.setProducts(product);
                cartItem.setQuantity(quantity); // Số lượng sản phẩm được truyền từ tham số quantity
                updateTotalPrice(cartItem);
                cartRepository.save(cartItem);
                product.setQuantity(stockQuantity - quantity);
                productService.saveProduct(product);
            }
            int totalQuantity=stockQuantity-quantity;
//            System.out.println("abc "+totalQuantity);
            if (totalQuantity <= 0) {
                product.setStatus(false);
            }
            productService.saveProduct(product);
        }
    }

    @Override
    public void removeToCart(Long cartItemId) {
        Optional<Cart> cartItemOptional = cartRepository.findById(cartItemId);

        if (cartItemOptional.isPresent()) {
            Cart cartItem = cartItemOptional.get();
            int quantity = cartItem.getQuantity();
            Products product = cartItem.getProducts();

            // Trả lại số lượng sản phẩm vào số lượng trong kho
            product.setQuantity(product.getQuantity() + quantity);
            product.setStatus(true);
            // Lưu lại sản phẩm
            productService.saveProduct(product);

            // Xóa Cart item khỏi giỏ hàng
            cartRepository.deleteById(cartItemId);
        }
    }

    @Override
    public List<Cart> getCartToUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }


    @Override
    public void deletePaidCartItems(Long userId) {
        List<Cart> cartList = cartRepository.findByUserId(userId);
        cartRepository.deleteAll(cartList);
    }

    @Override
    public List<Cart> getCartItems(Long userId) {
        return cartRepository.findByUserId(userId);
    }

    @Override
    public BigDecimal calculateTotalPrice(List<Cart> cartItem) {
        BigDecimal total = BigDecimal.ZERO;
        for (Cart item : cartItem) {
            total = total.add(item.getTotalPrice());
        }
        return total;
    }

    @Override
    public BigDecimal calculateDiscount(List<Cart> cartItems) {
        BigDecimal total = BigDecimal.ZERO;
        int quantity = 0;
        for (Cart cartItem : cartItems) {
            total = total.add(cartItem.getTotalPrice());
            quantity += cartItem.getQuantity();
        }
        if (quantity >= 2 && quantity < 5) {
            BigDecimal discount = total.multiply(new BigDecimal("0.05")); // Giảm giá 5%
            total = total.subtract(discount);
        } else if (quantity >= 5 && quantity < 10) {
            BigDecimal discount = total.multiply(new BigDecimal("0.1")); // Giảm giá 10%
            total = total.subtract(discount);
        } else if (quantity >= 10) {
            BigDecimal discount = total.multiply(new BigDecimal("0.2")); // Giảm giá 20%
            total = total.subtract(discount);
        }
        return total;
    }

    @Override
    public int getCartItemCount(Long userId) {
        List<Cart> cartList = cartRepository.findByUserId(userId);
        int itemCount = 0;
        for (Cart cart : cartList) {
            itemCount += cart.getQuantity();
        }
        return itemCount;
    }

    @Override
    public Long createOrderFromCart(Long userId) {
        List<Cart> cartList = cartRepository.findByUserId(userId);
        BigDecimal totalAmount = calculateTotalPrice(cartList);
        BigDecimal discount = totalAmount.subtract(calculateDiscount(cartList));

        User currentUser = userService.findById(userId);

        Order order = new Order();
        order.setUser(currentUser);
        order.setDiscount(discount);
        order.setTotal(totalAmount.subtract(discount));
        order.setStatus("Pending");
        order.setUpdatedAt(new Date());
        Order savedOrder = orderService.saveOrder(order);
        saveOrderItem(cartList, discount, savedOrder, orderItemService);
        deletePaidCartItems(userId);
        return savedOrder.getId();
    }
    @Override
    public BigDecimal getTotalPriceByUserId(Long userId) {
        return cartRepository.findTotalPriceByUserId(userId);
    }

    private void updateTotalPrice(Cart cartItem) {
        BigDecimal totalPrice = cartItem.getProducts().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));
        cartItem.setTotalPrice(totalPrice);
    }
    private void saveOrderItem(List<Cart> cartList, BigDecimal discount, Order savedOrder, OrderItemService orderItemService) {
        for (Cart item : cartList) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(savedOrder);
            orderItem.setProducts(item.getProducts());
            orderItem.setPrice(item.getProducts().getPrice());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setDiscount(discount);
            orderItem.setCreatedAt(new Date());
            orderItem.setUpdatedAt(new Date());
            orderItemService.save(orderItem);
        }
    }
}
