package com.main.laptop_world.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping({"/" ,"/home" ,"/index"})
    public String index(Model model){
        model.addAttribute("title","Laptop World - Thế giới Laptop");
        return "index";
    }
}
