package com.ikubinfo.Internship.repository;

import com.ikubinfo.Internship.entity.Financier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FinancierRepo extends JpaRepository<Financier, Long> {
    Boolean existsByFinancierDetails_Username(String username);
    Integer deleteByFinancierDetails_Username(String name);
    Financier getByFinancierDetails_Username(String username);
    @Query(value = "SELECT * FROM financier WHERE username = ?1 and is_deleted = true" ,nativeQuery = true)
    Financier getFromHistory(String username);
}
