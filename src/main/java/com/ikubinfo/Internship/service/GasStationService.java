package com.ikubinfo.Internship.service;

import com.ikubinfo.Internship.entity.GasStation;
import com.ikubinfo.Internship.repository.GasStationRepo;
import org.springframework.stereotype.Service;

@Service
public class GasStationService {
    private GasStationRepo gasStationRepo;

    public GasStationService(GasStationRepo gasStationRepo) {
        this.gasStationRepo = gasStationRepo;
    }

    public GasStation getGasStation(String name){
        return gasStationRepo.getByName(name);
    }
}
