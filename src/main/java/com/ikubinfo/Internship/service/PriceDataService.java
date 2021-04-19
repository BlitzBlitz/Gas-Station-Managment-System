package com.ikubinfo.Internship.service;

import com.ikubinfo.Internship.entity.Fuel;
import com.ikubinfo.Internship.entity.PriceData;
import com.ikubinfo.Internship.repository.PriceDataRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class PriceDataService {
    private final PriceDataRepo priceDataRepo;

    @Autowired
    public PriceDataService(PriceDataRepo priceDataRepo) {
        this.priceDataRepo = priceDataRepo;
    }


    @Transactional
    public Fuel saveData(PriceData priceData) {
        Double newPrice = priceData.getPrice();                              //get new price
        priceData.setPrice(priceData.getFuelType().getCurrentPrice());      //get old price

        priceData.getFuelType().setCurrentPrice(newPrice);                  //
        priceDataRepo.save(priceData);
        return priceData.getFuelType();
    }
}
