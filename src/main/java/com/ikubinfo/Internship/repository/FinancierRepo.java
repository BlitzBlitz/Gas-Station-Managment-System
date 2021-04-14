package com.ikubinfo.Internship.repository;

import com.ikubinfo.Internship.entity.Financier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FinancierRepo extends JpaRepository<Financier, Long> {
    List<Financier> getAllByAdmin_Id(Long adminId);
    Financier getByAdmin_IdAndId(Long adminId, Long financierId);
    Void deleteAllByAdmin_Id(Long adminId);
}
