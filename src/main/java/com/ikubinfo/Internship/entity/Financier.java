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
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private Admin admin;

    @OneToMany(mappedBy = "boughtByFinancier")
    private List<FuelSupplyData> fuelSupplyDataList = new ArrayList<>();

    @OneToOne(mappedBy = "accountedBy", fetch = FetchType.LAZY)
    private GasStation gasStations;

    public Financier(Long id, String username, String password, Double salary, GasStation gasStations) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.salary = salary;
    }

}
