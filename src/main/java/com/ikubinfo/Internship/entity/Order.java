package com.ikubinfo.Internship.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "orders")              //  !!! postgres has the name "order" reserved
@Data
@NoArgsConstructor
public class Order implements Serializable {
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
    private Worker processedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    private Fuel fuelType;


    public Order(Fuel fuelType, Double amount, Worker worker, Double total, LocalDateTime orderDate) {
        this.fuelType = fuelType;
        this.amount = amount;
        this.processedBy = worker;
        this.total = total;
        this.orderDate = orderDate;
        this.orderDate.format(DateTimeFormatter.ofPattern("HH:mm:ss dd:MM:yyyy"));
    }
}
