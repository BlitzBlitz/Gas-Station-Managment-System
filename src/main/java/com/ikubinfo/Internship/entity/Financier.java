package com.ikubinfo.Internship.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
    @CreationTimestamp
    LocalDateTime createdAt;
    @UpdateTimestamp
    LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    Admin admin;

    @OneToMany(mappedBy = "boughtByFinancier")
    List<FuelSupplyData> fuelSupplyDataList = new ArrayList<>();


    public Financier(Long id, String username, String password, Double salary) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.salary = salary;
    }

}
