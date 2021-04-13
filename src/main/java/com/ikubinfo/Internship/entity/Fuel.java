package com.ikubinfo.Internship.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
public class Fuel {
    @Id
    @GeneratedValue
    Long id;
    @Column(nullable = false, unique = true)
    private String type;
    @Column(nullable = false)
    private Double currentPrice;
    @Column(nullable = false)
    private Double currentAvailableAmount;

    @OneToMany(mappedBy = "fuelType")
    private final List<PriceData> priceHistory = new ArrayList<>();

    @OneToMany(mappedBy = "fuelType")
    private final List<Order> orderHistory = new ArrayList<>();



    public Fuel(Long id, String type, Double currentPrice, Double currentAvailableAmount) {
        this.id = id;
        this.type = type;
        this.currentPrice = currentPrice;
        this.currentAvailableAmount = currentAvailableAmount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public List<PriceData> getPriceHistory() {
        return priceHistory;
    }

    public void addPriceUpdate(PriceData priceUpdate) {
        this.priceHistory.add(priceUpdate);
    }

    public List<Order> getOrderHistory() {
        return orderHistory;
    }

    public Double getCurrentAvailableAmount() {
        return currentAvailableAmount;
    }

    public void setCurrentAvailableAmount(Double currentAvailableAmount) {
        this.currentAvailableAmount = currentAvailableAmount;
    }
    public boolean buy(Double amount){
        if(this.currentAvailableAmount - amount < 0){
            return false;
        }
        this.currentAvailableAmount -= amount;
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fuel fuel = (Fuel) o;
        return type.equals(fuel.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }
}
