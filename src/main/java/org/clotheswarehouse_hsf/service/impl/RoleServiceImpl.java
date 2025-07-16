package org.clotheswarehouse_hsf.service.impl;

import org.clotheswarehouse_hsf.model.Role;
import org.clotheswarehouse_hsf.repository.RoleRepository;
import org.clotheswarehouse_hsf.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<Role> getAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role findById(String roleId) {
        return roleRepository.findById(roleId).orElse(null);
    }
}
