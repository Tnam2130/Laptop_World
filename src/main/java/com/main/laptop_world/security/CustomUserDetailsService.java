package com.main.laptop_world.security;

import com.main.laptop_world.Entity.Role;
import com.main.laptop_world.Entity.User;
import com.main.laptop_world.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("abc");
        User user = userRepository.findByUsername(username);

        if (user != null) {
            System.out.println(user.getUsername());
            return new org.springframework.security.core.userdetails.User(user.getUsername(),
                    user.getPassword(),
                    mapRolesToAuthorities(user.getRoles()));

        } else {
            System.out.println(user.getUsername() + "null");
            throw new UsernameNotFoundException("Invalid username or password.");
        }

    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(List<Role> roles) {
        Collection<? extends GrantedAuthority> mapRoles = roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
        System.out.println(mapRoles);
        return mapRoles;
    }

}
