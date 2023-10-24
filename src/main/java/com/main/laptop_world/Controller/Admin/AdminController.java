package com.main.laptop_world.Controller.Admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/admin")
public class AdminController {

    @RequestMapping({"/home" ,"/index"})
    public String index(Model model){
        model.addAttribute("title", "Laptop World trang quản trị");
        return "admin/index";
    }

}
