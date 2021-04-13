package com.ikubinfo.Internship.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")              //  !!! postgres has the name "order" reserved
public class Order {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private Double amount;
    @Column(nullable = false)
    private Double total;

    @CreationTimestamp
    private LocalDateTime orderDate;

    @ManyToOne(fetch = FetchType.LAZY)
    Worker worker;

    @ManyToOne(fetch = FetchType.LAZY)
    private Fuel fuelType;



    public Order() {
    }

    public Order(Fuel fuelType, Double amount, Worker worker, Double total) {
        this.fuelType = fuelType;
        this.amount = amount;
        this.worker = worker;
        this.total = total;
    }

    public Order(Fuel fuelType,Long id, Double amount, Double total) {
        this.id = id;
        this.amount = amount;
        this.total = total;
        this.fuelType = fuelType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker workerId) {
        this.worker = workerId;
    }

    public Fuel getFuelType() {
        return fuelType;
    }

    public void setFuelType(Fuel fuelType) {
        this.fuelType = fuelType;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
