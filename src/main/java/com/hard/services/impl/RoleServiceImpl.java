package com.hard.services.impl;

import com.hard.models.Role;
import com.hard.repositories.RoleRepository;
import com.hard.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Collection<Role> getAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role getById(long id) {
        return roleRepository.findOne(id);
    }

    @Override
    public void add(Role role) {
        roleRepository.save(role);
    }

    @Override
    public void update(Role role) {
        roleRepository.saveAndFlush(role);
    }

    @Override
    public void delete(long id) {
        roleRepository.delete(id);
    }
}
