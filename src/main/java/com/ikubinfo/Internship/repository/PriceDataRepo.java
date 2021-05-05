package com.ikubinfo.Internship.repository;

import com.ikubinfo.Internship.entity.PriceData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriceDataRepo extends CrudRepository<PriceData, Long> {
    List<PriceData> getAllByFuelType_Id(Long id);
}
