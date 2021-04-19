package com.ikubinfo.Internship.repository;

import com.ikubinfo.Internship.entity.PriceData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriceDataRepo extends CrudRepository<PriceData, Long> {
    @Query(value = "select * from price_data where fuel_type_id =?1", nativeQuery = true)
    List<PriceData> getAllByFuelType_Id(Long Id);
}
