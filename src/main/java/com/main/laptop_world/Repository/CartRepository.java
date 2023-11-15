package com.main.laptop_world.Repository;

import com.main.laptop_world.Entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByUserId(Long userId);
    Optional<Cart> findByUserIdAndProductsId(Long userId, Long productId);
    @Query("SELECT c.totalPrice from Cart c WHERE c.user.id =:userId")
    BigDecimal findTotalPriceByUserId(@Param("userId") Long userid);
}
