package com.main.laptop_world.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

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
            "/send-code",
            "/do-sendCode",
            "/check-code",
            "/do-checkCode",
            "/resetPassword",
            "/do-resetPassword",
            "/c/**"
    };
    private static final String[] USER_RESOURCES={
            "/user/**",
            "/cart/**",
            "/order/**"
    };
    private static final String[] ADMIN_RESOURCES = {
            "/admin/**"
    };
    private static final String[] EMPLOYEE_RESOURCES ={
            "/admin/home",
            "/admin/products/**"
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
                        .requestMatchers(ADMIN_RESOURCES).hasAnyAuthority("ADMIN")
                        .requestMatchers(USER_RESOURCES).hasAuthority("USER")
                        .requestMatchers(EMPLOYEE_RESOURCES).hasRole("EMPLOYEE")
                        .anyRequest().authenticated());
        http.rememberMe((remember -> remember.rememberMeServices(rememberMeServices).tokenValiditySeconds(86400).rememberMeParameter("remember-me")));
        http.formLogin(c -> c.loginPage("/login")
                .defaultSuccessUrl("/")
                .failureUrl("/login?error")
                .permitAll());
        http.logout(c -> c.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .deleteCookies("JSESSIONID")
                .permitAll()
                .logoutSuccessUrl("/"));
        return http.build();
    }
}
