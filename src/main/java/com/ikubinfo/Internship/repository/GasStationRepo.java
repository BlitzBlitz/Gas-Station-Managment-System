package com.ikubinfo.Internship.repository;

import com.ikubinfo.Internship.entity.GasStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GasStationRepo extends JpaRepository<GasStation, Long> {
    GasStation getByName(String name);
}
