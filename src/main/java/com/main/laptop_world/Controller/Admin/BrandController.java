package com.main.laptop_world.Controller.Admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class BrandController {
    @GetMapping("/brands")
    public String quanLyNhanHieuPage() {
        return "admin/quanLyNhanHieu";
    }
}
