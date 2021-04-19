package com.ikubinfo.Internship.repository;

import com.ikubinfo.Internship.entity.FuelSupplyData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuelSupplyDataRepo extends JpaRepository<FuelSupplyData, Long> {
}
