package com.hard.services;

import com.hard.models.Role;

import java.util.Collection;

public interface RoleService {
    Collection<Role> getAll();

    Role getById(long id);

    void add(Role role);

    void update(Role role);

    void delete(long id);
}
