package com.main.laptop_world.security.oauth2;

import com.main.laptop_world.Entity.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.*;
import java.util.stream.Collectors;

public class CustomOAuth2User implements OAuth2User {

    private final List<Role> roles;
    private final OAuth2User oAuth2User;

    public CustomOAuth2User(OAuth2User oAuth2User, List<Role> roles) {
        this.oAuth2User = oAuth2User;
        this.roles = roles;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return mapRolesToAuthorities(roles);
    }

    @Override
    public String getName() {
        return oAuth2User.getAttribute("email");
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(List<Role> roles) {
        Collection<? extends GrantedAuthority> mapRoles = roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                .collect(Collectors.toList());
        System.out.println(mapRoles);
        return mapRoles;
    }
}
