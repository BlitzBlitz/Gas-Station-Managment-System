package com.ikubinfo.Internship.repository;

import com.ikubinfo.Internship.entity.Fuel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuelRepo extends CrudRepository<Fuel, String> {
    Fuel findByType(String fuelType);
    Integer deleteByType(String fuelType);
}
