package com.main.laptop_world.Controller;

import com.main.laptop_world.Entity.User;
import com.main.laptop_world.Services.CartService;
import com.main.laptop_world.Services.GeneralService;
import com.main.laptop_world.Services.UserService;
import com.main.laptop_world.security.oauth2.CustomOAuth2User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;

@ControllerAdvice
public class HeaderController {
    private CartService cartService;
    private UserService userService;
    private GeneralService generalService;

    public HeaderController(CartService cartService, UserService userService, GeneralService generalService) {
        this.cartService = cartService;
        this.userService = userService;
        this.generalService = generalService;
    }

    @ModelAttribute("cartItemCount")
    public int getCartItemCount(Principal principal) {
        if (principal != null) {
            if (principal instanceof OAuth2AuthenticationToken) {
                Long userId = generalService.usernameHandler(principal);
                if (userId != null) {
                    return cartService.getCartItemCount(userId);
                }
            } else if (principal instanceof UsernamePasswordAuthenticationToken) {
                String principalName = principal.getName();
                if (principalName != null && !principalName.isEmpty()) {
                    User user = userService.findByUsername(principalName);
                    if (user != null) {
                        Long userId = user.getId();
                        if (userId != null) {
                            return cartService.getCartItemCount(userId);
                        }
                    }
                }
            }
        }
        return 0;
    }
}
