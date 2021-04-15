package com.ikubinfo.Internship.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
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
    @NotNull
    private Admin admin;

    @OneToMany(mappedBy = "processedBy")
    List<Order> orders = new ArrayList<>();

    public Worker() {
    }

    public Worker(Long id, String name, String password, Double shiftBalance, Double salary) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.shiftBalance = shiftBalance;
        this.salary = salary;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getShiftBalance() {
        return shiftBalance;
    }

    public void setShiftBalance(Double shiftBalance) {
        this.shiftBalance = shiftBalance;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void addOrder(Order order) {
        this.orders.add(order);
    }

    public void removeOrder(Order order) {
        this.orders.remove(order);
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }
}
