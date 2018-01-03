package com.hard.services.impl;

import com.hard.models.Role;
import com.hard.models.RoleList;
import com.hard.models.User;
import com.hard.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(s);

        String username = user.getUsername();
        String password = user.getPassword();
        Collection<GrantedAuthority> grantedAuthorities = new HashSet<>();

        Collection<Role> roles = user.getRoles();

        for (Role role : roles) {
            RoleList title = role.getTitle();
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(title.name());
            grantedAuthorities.add(grantedAuthority);
        }

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(username, password, grantedAuthorities);

        return userDetails;
    }
}
