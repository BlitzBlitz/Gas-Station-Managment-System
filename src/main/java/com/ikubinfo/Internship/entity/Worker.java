package com.ikubinfo.Internship.entity;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "update worker set is_deleted = true where id=?")
public class Worker implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private Double shiftBalance;
    @Column(nullable = false)
    private Double salary;
    private boolean isDeleted;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "username",referencedColumnName = "username")
    private User workerDetails;

    @OneToMany(mappedBy = "processedBy")
    private List<Order> orders = new ArrayList<>();

    public Worker(Double shiftBalance, Double salary) {
        this.shiftBalance = shiftBalance;
        this.salary = salary;
    }

}
