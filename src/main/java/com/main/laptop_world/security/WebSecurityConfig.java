package com.main.laptop_world.security;

import com.main.laptop_world.Constant.GlobalFlag;
import com.main.laptop_world.Entity.User;
import com.main.laptop_world.Services.UserService;
import com.main.laptop_world.security.oauth2.CustomOAuth2User;
import com.main.laptop_world.security.oauth2.CustomOAuth2UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {
    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;
    @Autowired
    private UserService userService;

    private static final String[] PUBLIC_RESOURCES = {
            "/resources/**",
            "/static/**",
            "/static/images/products/**",
            "/bootstrapv5/**",
            "/css/**",
            "/images/**",
            "/js/**",
            "/uploads/**",
            "/",
            "/index",
            "/home",
            "/login/**",
            "/register/**",
            "/collections/**",
            "/search/**",
            "/send-code/**",
            "/do-sendCode/**",
            "/check-code/**",
            "/do-checkCode/**",
            "/resetPassword/**",
            "/pay/**",
            "/do-resetPassword/**",
            "/c/**"
    };
    private static final String[] USER_RESOURCES = {
            "/user/**",
            "/cart/**",
            "/order/**",
            "/contact/**"
    };
    private static final String[] ADMIN_RESOURCES = {
            "/admin/**"
    };

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    RememberMeServices rememberMeServices(UserDetailsService userDetailsService) {
        TokenBasedRememberMeServices.RememberMeTokenAlgorithm encodingAlgorithm = TokenBasedRememberMeServices.RememberMeTokenAlgorithm.SHA256;
        String key = "SECRET14985748639785";
        TokenBasedRememberMeServices rememberMe = new TokenBasedRememberMeServices(key, userDetailsService, encodingAlgorithm);
        rememberMe.setMatchingAlgorithm(TokenBasedRememberMeServices.RememberMeTokenAlgorithm.MD5);
        return rememberMe;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, RememberMeServices rememberMeServices) throws Exception {
        http.authorizeHttpRequests(c ->
                c
                        .requestMatchers(PUBLIC_RESOURCES).permitAll()
                        .requestMatchers(ADMIN_RESOURCES).hasRole("ADMIN")
                        .requestMatchers(USER_RESOURCES).hasRole("USER")
                        .anyRequest().authenticated());
        http.rememberMe((remember -> remember.rememberMeServices(rememberMeServices).tokenValiditySeconds(86400).rememberMeParameter("remember-me")));
        http.formLogin(c ->
                c.loginPage("/login")
                        .defaultSuccessUrl("/")
                        .failureUrl("/login?error")
                        .permitAll());
        http.oauth2Login(c -> {
                    try {
                        c.loginPage("/login").authorizationEndpoint(authorization -> authorization
                                        .baseUri("/login/oauth2/authorization")
                                        .baseUri("/oauth2/authorization")
                                )
                                .userInfoEndpoint(userInfo -> userInfo
                                        .userService(customOAuth2UserService)
                                )
                                .successHandler((request, response, authentication) -> {
                                    if (authentication.getPrincipal() instanceof CustomOAuth2User customOAuth2User) {
                                        String email = customOAuth2User.getName();
                                        User existingUser = userService.findByEmail(email);
                                        if (existingUser != null) {
                                            if (existingUser.isActive()){
                                                GlobalFlag.flag=true;
                                                response.sendRedirect("/");
                                                System.out.println("User is existing!");
                                            }
                                            GlobalFlag.flag=false;
                                            response.sendRedirect("/login?error");
                                        } else {
                                            String username = customOAuth2User.getUsername();
                                            String clientName = customOAuth2User.getOauth2ClientNames();
                                            ;
                                            System.out.println("email: " + email + ", client: " + clientName +
                                                    ", username: " + username);
                                            userService.processOAuthPostLogin(email, clientName);
                                            response.sendRedirect("/");
                                        }
                                    } else {
                                        // Handle other cases if needed
                                        response.sendRedirect("/login"); // Redirect to login page in case of an issue
                                    }
                                });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

        );
        http.logout(c -> c.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .deleteCookies("JSESSIONID")
                .permitAll()
                .logoutSuccessUrl("/login?logout"));
        return http.build();
    }
}
