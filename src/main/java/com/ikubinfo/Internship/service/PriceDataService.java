package com.ikubinfo.Internship.service;

import com.ikubinfo.Internship.entity.Fuel;
import com.ikubinfo.Internship.entity.PriceData;
import com.ikubinfo.Internship.repository.PriceDataRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class PriceDataService {
    private PriceDataRepo priceDataRepo;

    @Autowired
    public PriceDataService(PriceDataRepo priceDataRepo) {
        this.priceDataRepo = priceDataRepo;
    }




    @Transactional
    public Fuel saveData(PriceData priceData){
        Double newPrice = priceData.getPrice();
        priceData.setPrice(priceData.getFuelType().getCurrentPrice());
        priceData.getFuelType().setCurrentPrice(newPrice);
        priceDataRepo.save(priceData);
        return priceData.getFuelType();
    }
}
