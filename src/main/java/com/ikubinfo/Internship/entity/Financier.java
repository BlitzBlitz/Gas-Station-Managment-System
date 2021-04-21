package com.ikubinfo.Internship.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Financier {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false, unique = true)
    private String username;
    private String password;
    private Double salary;
    private Double gasStationBalance = 0.0;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;


    @OneToMany(mappedBy = "boughtByFinancier")
    private List<FuelSupplyData> fuelSupplyDataList = new ArrayList<>();


    public Financier(Long id, String username, String password, Double salary) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.salary = salary;
    }

    public Double invest(Double amount){
        this.gasStationBalance += amount;
        return this.gasStationBalance;
    }

    public Double collectShiftPayments(Double dailyPayment){
        this.gasStationBalance += dailyPayment;
        return this.gasStationBalance;
    }

}
