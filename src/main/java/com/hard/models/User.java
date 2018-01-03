package com.hard.models;

import javax.persistence.*;
import java.util.Set;

@javax.persistence.Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = {
                    @JoinColumn(
                            name = "user_id"
                    ),
            },
            inverseJoinColumns = {
                    @JoinColumn(
                            name = "role_id"
                    )
            }
    )
    private Set<Role> roles;

    public User() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
//                ", roles=" + roles +
                '}';
    }
}
