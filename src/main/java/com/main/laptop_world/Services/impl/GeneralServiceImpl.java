package com.main.laptop_world.Services.impl;

import com.main.laptop_world.Entity.User;
import com.main.laptop_world.Services.GeneralService;
import com.main.laptop_world.Services.UserService;
import com.main.laptop_world.security.oauth2.CustomOAuth2User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class GeneralServiceImpl implements GeneralService {
    private UserService userService;

    public GeneralServiceImpl(UserService userService){
        this.userService=userService;
    }
    @Override
    public Long usernameHandler(Principal principal) {
        return getUsername(principal, userService);
    }

    public static Long getUsername(Principal principal, UserService userService) {
        String username= principal.getName();
        User user = userService.findByUsername(username);

        CustomOAuth2User oauthUser = (CustomOAuth2User) ((Authentication) principal).getPrincipal();
        if(user == null){
            String existingEmail=oauthUser.getUsername();
            user = userService.findByUsername(existingEmail);
            System.out.println(existingEmail);
        }
        return user.getId();
    }
}
