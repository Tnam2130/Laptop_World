package com.main.laptop_world.Services.impl;

import com.main.laptop_world.Entity.Role;
import com.main.laptop_world.Repository.RoleRepository;
import com.main.laptop_world.Repository.UserRepository;
import com.main.laptop_world.Services.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    RoleRepository roleRepository;
    UserRepository userRepository;
    public RoleServiceImpl(RoleRepository roleRepository, UserRepository userRepository){
        this.roleRepository=roleRepository;
        this.userRepository=userRepository;
    }

    @Override
    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public List<Role> getRoleByIds(List<Integer> roleIds) {
        return roleRepository.findAllById(roleIds);
    }

}
