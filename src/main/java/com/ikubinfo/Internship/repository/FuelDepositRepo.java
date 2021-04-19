package com.ikubinfo.Internship.repository;

import com.ikubinfo.Internship.entity.FuelDeposit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuelDepositRepo extends JpaRepository<FuelDeposit, Long> {
    FuelDeposit getByFuelType_Type(String fuelType);
}
