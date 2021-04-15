package com.ikubinfo.Internship.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GasStation {
    @Id
    @GeneratedValue
    Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double balance;

    @ManyToOne(fetch = FetchType.LAZY)
    private Admin administratedBy;

    @OneToOne(fetch = FetchType.LAZY)
    private Financier accountedBy;

    @OneToMany(mappedBy = "processedAt")
    private List<Order> orders = new ArrayList<>();
}
