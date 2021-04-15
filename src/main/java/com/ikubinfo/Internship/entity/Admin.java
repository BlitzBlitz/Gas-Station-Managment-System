package com.ikubinfo.Internship.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
public class Admin{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false)
    private String password;
    @CreationTimestamp
    LocalDateTime registeredAt;

    @OneToMany(mappedBy = "admin")
    List<Worker> workers = new ArrayList<>();
    @OneToMany(mappedBy = "admin")
    List<Financier> financiers = new ArrayList<>();
    @OneToMany(mappedBy = "changedBy")
    List<PriceData> priceData = new ArrayList<>();
    @OneToMany(mappedBy = "administratedBy")
    List<GasStation> gasStations = new ArrayList<>();

}
