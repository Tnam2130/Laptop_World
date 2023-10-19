package com.main.laptop_world.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/admin")
public class AdminController {


    @RequestMapping({"/home" ,"/index"})
    public String index(Model model){
        return "admin/index";
    }
    @GetMapping("/brands")
    public String quanLyNhanHieuPage() {
        return "admin/quanLyNhanHieu";
    }
    @GetMapping("/products")
    public String quanLySanPhamPage() {
        return "admin/quanLySanPham";
    }
    @GetMapping("/accounts")
    public String quanLyTaiKhoanPage() {
        return "admin/quanLyTaiKhoan";
    }
    @GetMapping("/dashboard")
    public String thongKePage() {
        return "admin/thongKe";
    }
    @GetMapping("/profile")
    public String proFilePage() {
        return "admin/profile";
    }
    @GetMapping("/categories")
    public String quanLyDanhMucPage() {
        return "admin/quanLyDanhMuc";
    }
}
