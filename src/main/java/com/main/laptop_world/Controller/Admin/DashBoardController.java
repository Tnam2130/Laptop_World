package com.main.laptop_world.Controller.Admin;

import com.main.laptop_world.Entity.Order;
import com.main.laptop_world.Repository.OrderRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class DashBoardController {
    OrderRepository orderRepository;
    public DashBoardController( OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }
    @GetMapping("admin/dashboard")
    public String thongKePage(Model model) {
        List<Order> orders = orderRepository.findAll();

        Map<String, BigDecimal> revenueByMonth = calculateRevenueByMonth(orders);
        model.addAttribute("revenueByMonth", revenueByMonth);
        return "admin/ThongKe";
    }

    private Map<String, BigDecimal> calculateRevenueByMonth(List<Order> orders) {
        Map<String, BigDecimal> revenueByMonth = new HashMap<>();

        for (Order order : orders) {
            // Lấy tháng và năm từ trường createdAt của entity Order
            YearMonth yearMonth = YearMonth.from(order.getCreatedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

            // Chuyển đổi thành chuỗi tháng/năm (ví dụ: "Tháng 1/2023")
            String monthYear = "Tháng " + yearMonth.getMonthValue() + "/" + yearMonth.getYear();

            // Tính tổng doanh thu cho tháng hiện tại
            BigDecimal total = revenueByMonth.getOrDefault(monthYear, BigDecimal.ZERO);
            total = total.add(order.getTotal());
            revenueByMonth.put(monthYear, total);
        }
        return revenueByMonth;
    }

}
