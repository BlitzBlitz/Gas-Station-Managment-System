package com.ikubinfo.Internship.repository;

import com.ikubinfo.Internship.entity.Financier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinancierRepo extends JpaRepository<Financier, Long> {
    Void deleteByUsername(String name);
    Financier getByUsername(String username);
    Financier getById(Long id);
}
