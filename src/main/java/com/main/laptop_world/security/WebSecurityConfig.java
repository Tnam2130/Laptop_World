package com.main.laptop_world.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {
    private static final String[] PUBLIC_RESOURCES={
            "/resources/**",
            "/static/**",
            "/bootstrapv5/**",
            "/css/**",
            "/images/**",
            "/js/**",
            "/admin/**",
            "/",
            "/index",
            "/home",
            "/login/**",
            "/register/**",
            "/cart/**",
            "/collections/**",
            "/c/**"
    };
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(c ->
                c
                        .requestMatchers("/users").hasAuthority("ADMIN")
                        .requestMatchers(PUBLIC_RESOURCES).permitAll()
                        .anyRequest().authenticated());
        http.formLogin(c -> c.loginPage("/login")
                .defaultSuccessUrl("/")
                .failureUrl("/login?error")
                .permitAll());
        http.logout(c -> c.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .permitAll()
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/"));
        return http.build();
    }
}
