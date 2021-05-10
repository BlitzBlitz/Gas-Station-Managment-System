package com.ikubinfo.Internship.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "update financier set is_deleted = true where id=?")
public class Financier implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private Double salary;
    private Double gasStationBalance = 0.0;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    private boolean isDeleted = false;

    @ManyToOne
    @JoinColumn(name = "username",referencedColumnName = "username")
    @Where(clause = "enabled = 1")
    private User financierDetails;


    @OneToMany(mappedBy = "boughtByFinancier")
    private List<FuelSupplyData> fuelSupplyDataList = new ArrayList<>();

    public Financier(Long id, Double salary, Double gasStationBalance, boolean isDeleted, User financierDetails) {
        this.id = id;
        this.salary = salary;
        this.gasStationBalance = gasStationBalance;
        this.isDeleted = isDeleted;
        this.financierDetails = financierDetails;
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
