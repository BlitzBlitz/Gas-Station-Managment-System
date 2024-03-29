package com.ikubinfo.Internship.repository;

import com.ikubinfo.Internship.entity.Fuel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FuelRepo extends CrudRepository<Fuel, Long> {
    Fuel findByType(String fuelType);
    Integer deleteByType(String fuelType);
    List<Fuel> findAll();
    Fuel getByType(String type);
    Boolean existsByType(String fuelType);

    @Query(value = "select * from fuel where type = ?1", nativeQuery = true)
    Fuel findByTypeForHistory(String fuelType);
}
