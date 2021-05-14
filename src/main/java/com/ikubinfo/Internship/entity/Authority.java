package com.ikubinfo.Internship.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "authorities")
@Data
@NoArgsConstructor
public class Authority implements Serializable {
    @Id
    @GeneratedValue
    Long id;
    @ManyToOne
    @JoinColumn(name = "username",referencedColumnName = "username")
    UserD username;
    @Column(nullable = false)
    private String authority;

    public Authority(String authority) {
        this.authority = authority;
    }
}
