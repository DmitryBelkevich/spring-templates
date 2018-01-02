package com.hard.services;

import com.hard.models.User;

import java.util.Collection;

public interface UserService {
    Collection<User> getAll();
    User getById(long id);
    User getByUsername(String username);
    void add(User user);
    void update(User user);
    void delete(long id);
}
