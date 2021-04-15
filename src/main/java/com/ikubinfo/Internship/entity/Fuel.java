package com.ikubinfo.Internship.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@SQLDelete(sql = "update fuel set is_deleted = true where id=?")
@Where(clause = "is_deleted = false")
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

    @OneToMany(mappedBy = "fuelType")
    private List<FuelSupplyData> fuelSupplyDataList = new ArrayList<>();

    private boolean isDeleted = false;

    public Fuel(Long id, String type, Double currentPrice, Double currentAvailableAmount) {
        this.id = id;
        this.type = type;
        this.currentPrice = currentPrice;
        this.currentAvailableAmount = currentAvailableAmount;
    }

    @PreRemove
    private void preRemove(){
        this.isDeleted = true;
    }
}
