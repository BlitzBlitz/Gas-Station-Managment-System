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
@NoArgsConstructor
@AllArgsConstructor
@Data
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "update admin set is_deleted = true where id=?")
public class Admin implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CreationTimestamp
    private LocalDateTime registeredAt;
    @UpdateTimestamp
    private LocalDateTime updateAt;
    private boolean isDeleted = false;

    @ManyToOne
    @JoinColumn(name = "username",referencedColumnName = "username")
    @Where(clause = "enabled = 1")
    private User adminDetails;

    @OneToMany(mappedBy = "changedBy")
    private List<PriceData> priceData = new ArrayList<>();



}
