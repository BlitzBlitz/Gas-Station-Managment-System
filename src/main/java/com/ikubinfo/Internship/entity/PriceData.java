package com.ikubinfo.Internship.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
public class PriceData{
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Fuel fuelType;
    @CreationTimestamp
    private Date priceUpdatedDate;
    private Double price;


    public PriceData() {
    }

    public PriceData(Fuel fuelType, Double price) {
        this.fuelType = fuelType;
        this.price = price;
    }

    public Fuel getFuelType() {
        return fuelType;
    }

    public void setFuelType(Fuel fuelType) {
        this.fuelType = fuelType;
    }

    public Date getPriceUpdatedDate() {
        return priceUpdatedDate;
    }

    public void setPriceUpdatedDate(Date priceUpdatedDate) {
        this.priceUpdatedDate = priceUpdatedDate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "PriceData{" +
                "fuelType= " + fuelType.getType()+
                ", priceUpdatedDate= " + priceUpdatedDate +
                ", price= " + price +
                '}';
    }
}
