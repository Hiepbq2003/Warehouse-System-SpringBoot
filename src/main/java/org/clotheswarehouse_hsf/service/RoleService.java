package org.clotheswarehouse_hsf.service;

import org.clotheswarehouse_hsf.model.Role;

import java.util.List;

public interface RoleService {
    List<Role> getAll();

    Role findById(String roleId);
}
