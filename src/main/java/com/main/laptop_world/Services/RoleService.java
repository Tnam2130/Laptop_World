package com.main.laptop_world.Services;

import com.main.laptop_world.Entity.Role;

import java.util.List;

public interface RoleService {
    List<Role> findAllRoles();
    List<Role> getRoleByIds(List<Integer> roleIds);
}
