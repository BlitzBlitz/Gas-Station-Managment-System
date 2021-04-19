package com.ikubinfo.Internship.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceData{
    @Id
    @GeneratedValue
    private Long id;
    private Double price;

    @ManyToOne(fetch = FetchType.LAZY)
    private Fuel fuelType;
    @ManyToOne(fetch = FetchType.LAZY)
    private Admin changedBy;
    @CreationTimestamp
    private Date priceChangedDate;

    public PriceData(Double price, Fuel fuelType, Admin changedBy) {
        this.price = price;
        this.fuelType = fuelType;
        this.changedBy = changedBy;
    }
}
