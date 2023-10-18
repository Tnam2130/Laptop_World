package com.main.laptop_world.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class adminController {


    @RequestMapping({"/" ,"/home" ,"/index"})
    public String index(Model model){

        return "admin/index";
    }
    @GetMapping("/admin/nhan-hieu")
    public String quanLyNhanHieuPage() {
        return "admin/quanLyNhanHieu";
    }
    @GetMapping("/admin/san-pham")
    public String quanLySanPhamPage() {
        return "admin/quanLySanPham";
    }
    @GetMapping("/admin/tai-khoan")
    public String quanLyTaiKhoanPage() {
        return "admin/quanLyTaiKhoan";
    }
    @GetMapping("/admin/thong-ke")
    public String thongKePage() {
        return "admin/thongKe";
    }
    @GetMapping("/admin/profile")
    public String proFilePage() {
        return "admin/profile";
    }
    @GetMapping("/admin/danh-muc")
    public String quanLyDanhMucPage() {
        return "admin/quanLyDanhMuc";
    }
}
