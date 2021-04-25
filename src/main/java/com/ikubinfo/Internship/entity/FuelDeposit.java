package com.ikubinfo.Internship.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FuelDeposit implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Double availableAmount;

    @OneToOne(mappedBy = "fuelDeposit",fetch = FetchType.LAZY)
    private Fuel fuelType;

    public FuelDeposit (Double availableAmount) {
        this.availableAmount = availableAmount;
    }
    public void addFuel(Double amount){
        this.availableAmount += amount;
    }
}
