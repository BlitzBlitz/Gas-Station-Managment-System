package com.ikubinfo.Internship.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
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
    List<PriceData> priceData = new ArrayList<PriceData>();

    public Admin() {
    }

    public Admin(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Worker> getWorkers() {
        return workers;
    }

    public void addWorker(Worker worker) {
        this.workers.add( worker);
    }
    public void removeWorker(Worker worker) {
        this.workers.remove( worker);
    }

    public LocalDateTime getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(LocalDateTime registeredAt) {
        this.registeredAt = registeredAt;
    }

    public List<Financier> getFinanciers() {
        return financiers;
    }

    public void addFinancier(Financier financier) {
        this.financiers.add(financier);
    }
    public void removeFinancier(Financier financier) {
        this.financiers.remove(financier);
    }

    public List<PriceData> getPriceData() {
        return priceData;
    }

    public void addPriceData(PriceData priceData) {
        this.priceData.add(priceData);
    }
    public void removePriceData(PriceData priceData) {
        this.priceData.remove(priceData);
    }
}
