package com.main.laptop_world.Controller.Admin;

import com.main.laptop_world.Entity.DTO.UserDTO;
import com.main.laptop_world.Entity.ProductVersion;
import com.main.laptop_world.Entity.User;
import com.main.laptop_world.Repository.UserRepository;
import com.main.laptop_world.Services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
//@RequestMapping("/admin")
public class AccountController {
    UserService userService;
    public AccountController(UserService userService){
        this.userService=userService;
    }
    @GetMapping("/admin/accounts")
    public String quanLyTaiKhoanPage(Model model) {
        List<User> users = userService.findAllUser();
        model.addAttribute("user", users);
        return "admin/QuanLyTaiKhoan";
    }


    @GetMapping(value = "/admin/accounts/delete/{id}")
    public String delete(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/accounts";
    }
    @GetMapping(value = "/admin/accounts/update/id={id}")
    public String edit(@PathVariable Long id, Model model, @ModelAttribute User user) {
        User users = userService.findById(id);
        model.addAttribute("users", users);
        return "admin/CRUDupdate/updateUser";
    }

    @PostMapping(value = "/admin/accounts/update")
    public String update(User user) {
        userService.updateUser(user);

        return "redirect:/admin/accounts";
    }

    @GetMapping(value ="/admin/accounts/add")
    public String add(Model model) {
        User userDTO = new User();
        model.addAttribute("user", userDTO);
        return "admin/QuanLyTaiKhoan";
    }

    @PostMapping(value ="/admin/accounts/add")
    public String save(@Valid @ModelAttribute("user") User user,
                       BindingResult result, Model model) {
        userService.save(user);
        return "redirect:/admin/accounts";
    }
}
