package com.hard.services.impl;

import com.hard.models.Role;
import com.hard.models.User;
import com.hard.repositories.RoleRepository;
import com.hard.repositories.UserRepository;
import com.hard.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Collection<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getById(long id) {
        return userRepository.findOne(id);
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void add(User user) {
        String password = user.getPassword();
        String encodedPassword = bCryptPasswordEncoder.encode(password);
        user.setPassword(encodedPassword);

        Set<Role> roles = new HashSet<>();
        Role role = roleRepository.findOne(1L);
        roles.add(role);
        user.setRoles(roles);

        userRepository.save(user);
    }

    @Override
    public void update(User user) {
        String password = user.getPassword();
        String encodedPassword = bCryptPasswordEncoder.encode(password);
        user.setPassword(encodedPassword);

        userRepository.saveAndFlush(user);
    }

    @Override
    public void delete(long id) {
        userRepository.delete(id);
    }
}
