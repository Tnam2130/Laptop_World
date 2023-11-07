package com.main.laptop_world.Controller.Admin;

import com.main.laptop_world.Entity.DTO.UserDTO;
import com.main.laptop_world.Entity.ProductVersion;
import com.main.laptop_world.Entity.Role;
import com.main.laptop_world.Entity.User;
import com.main.laptop_world.Repository.UserRepository;
import com.main.laptop_world.Services.RoleService;
import com.main.laptop_world.Services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
//@RequestMapping("/admin")
public class AccountController {
    UserService userService;
    RoleService roleService;
    public AccountController(UserService userService, RoleService roleService){
        this.userService=userService;
        this.roleService=roleService;
    }
    @GetMapping("/admin/accounts")
    public String quanLyTaiKhoanPage(Model model, Principal principal) {
        User currentUser=userService.findByUsername(principal.getName());
        List<User> users = userService.findAllUser();
        List<Role> roleList=roleService.findAllRoles();
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("roleList", roleList);
        model.addAttribute("users", users);
        return "admin/QuanLyTaiKhoan";
    }


    @GetMapping(value = "/admin/accounts/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        ra.addFlashAttribute("message", "Delete successfully");
        userService.deleteUser(id);
        return "redirect:/admin/accounts";
    }
    @GetMapping(value = "/admin/accounts/update/id={id}")
    public String edit(@PathVariable Long id, Model model, @ModelAttribute User user) {
        User users = userService.findById(id);
        model.addAttribute("users", users);
        return "admin/Update/updateUser";
    }

    @PostMapping(value = "/admin/accounts/update")
    public String update(User user, RedirectAttributes ra) {
        ra.addFlashAttribute("message", "Update successfully");
        userService.updateUser(user);

        return "redirect:/admin/accounts";
    }
}
