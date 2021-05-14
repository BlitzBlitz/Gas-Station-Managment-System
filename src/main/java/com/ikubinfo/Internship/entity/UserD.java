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
public class UserD implements Serializable {
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


    public UserD(String username, String encodedPassword) {
        this.username = username;
        this.password = encodedPassword;
        this.enabled = 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserD userD = (UserD) o;
        return username.equals(userD.username) &&
                password.equals(userD.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }
}
