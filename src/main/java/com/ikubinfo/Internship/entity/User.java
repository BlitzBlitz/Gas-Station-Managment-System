package com.ikubinfo.Internship.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private int enabled;

    @OneToMany(mappedBy = "username")
    private List<Authority> authorities;
    @OneToMany(mappedBy = "adminDetails")
    private List<Admin> admins = new ArrayList<>();
    @OneToMany(mappedBy = "workerDetails")
    private List<Worker> workers = new ArrayList<>();
    @OneToMany(mappedBy = "financierDetails")
    private List<Financier> financiers = new ArrayList<>();


    public User(String username, String encodedPassword) {
        this.username = username;
        this.password = encodedPassword;
        this.enabled = 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return username.equals(user.username) &&
                password.equals(user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }
}
