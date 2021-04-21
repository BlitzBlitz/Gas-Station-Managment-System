package com.ikubinfo.Internship.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Worker {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private Double shiftBalance;
    @Column(nullable = false)
    private Double salary;


    @ManyToOne(fetch = FetchType.LAZY)
    private Admin admin;

    @OneToMany(mappedBy = "processedBy")
    private List<Order> orders = new ArrayList<>();

    public Worker(String name, String password, Double shiftBalance, Double salary) {
        this.name = name;
        this.password = password;
        this.shiftBalance = shiftBalance;
        this.salary = salary;
    }

}
