package com.ikubinfo.Internship.service;

import com.ikubinfo.Internship.entity.Fuel;
import com.ikubinfo.Internship.entity.PriceData;
import com.ikubinfo.Internship.repository.FuelRepo;
import com.ikubinfo.Internship.repository.PriceDataRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class FuelService {
    private final FuelRepo fuelRepo;
    private final PriceDataRepo priceRepo;

    @Autowired
    public FuelService(FuelRepo fuelRepo, PriceDataRepo priceRepo) {
        this.fuelRepo = fuelRepo;
        this.priceRepo = priceRepo;
    }



    public void addFuel(Fuel newFuelType) throws EntityExistsException{
        try {
            fuelRepo.save(newFuelType);             //because the "name" column has a unique constrain this will throw an Exc if trying to register twice
        }catch (Exception e){
            throw new EntityExistsException("This type of fuel already exists, try changing its price.");
        }
    }
    public void changePrice(String fuelType, Double newPrice) throws EntityNotFoundException {
        Fuel found = fuelRepo.findByType(fuelType);
        if(found == null){
            throw new EntityNotFoundException();
        }
        if(newPrice > 0){
            priceRepo.save(new PriceData(found, found.getCurrentPrice()));
            found.setCurrentPrice(newPrice);
            fuelRepo.save(found);
        }else {
            return;                         // what to do here throw or return ????????????????????????????????
        }

    }

    @Transactional
    public void removeFuelType(String fuelType) throws EntityNotFoundException{
        //should i delete the price history >???????????????????????????
        int result = fuelRepo.deleteByType(fuelType);
        System.out.println(result);
        if( result == 0){
            throw new EntityNotFoundException("Fuel type does not exist!");
        }
    }

    @Transactional
    //https://www.netsurfingzone.com/hibernate/failed-to-lazily-initialize-a-collection-of-role-could-not-initialize-proxy-no-session/
    public List<PriceData> getFuelPriceHistory(String fuelType) throws EntityNotFoundException{
        Fuel found = fuelRepo.findByType(fuelType);
        if(found == null){
            throw new EntityNotFoundException();
        }
        return found.getPriceHistory();             //you should add "spring.jpa.properties.hibernate.enable_lazy_load_no_trans=true" ????????????????????????????
    }
    public Fuel getFuel(String fuelType){
        return fuelRepo.findByType(fuelType);
    }
    @Transactional
    public Double buyFuel(Fuel found, Double buyingAmount) throws EntityNotFoundException{
        if(found.buy(buyingAmount)){
            try {
                fuelRepo.save(found);
            }catch (Exception e){
                throw new PersistenceException("Error buying fuel!");
            }
            return found.getCurrentPrice()*buyingAmount;
        }
        return -1.0;
    }
}
