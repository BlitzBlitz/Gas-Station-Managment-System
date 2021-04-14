package com.ikubinfo.Internship.repository;

import com.ikubinfo.Internship.entity.PriceData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceDataRepo extends CrudRepository<PriceData, Long> {
}
