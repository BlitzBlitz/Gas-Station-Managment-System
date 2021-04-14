package com.ikubinfo.Internship.service;

import com.ikubinfo.Internship.entity.Fuel;
import com.ikubinfo.Internship.entity.PriceData;
import com.ikubinfo.Internship.repository.FuelRepo;
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
    private final PriceDataService priceDataService;

    @Autowired
    public FuelService(FuelRepo fuelRepo, PriceDataService priceDataService) {
        this.fuelRepo = fuelRepo;
        this.priceDataService = priceDataService;
    }

    public List<Fuel> getAllFuels(){
        return fuelRepo.findAll();
    }

    public Fuel getFuel(String type){
        return fuelRepo.getByType(type);
    }

    @Transactional
    public Fuel addFuel(Fuel newFuelType) throws EntityExistsException{
        try {
            return fuelRepo.save(newFuelType);             //because the "name" column has a unique constrain this will throw an Exc if trying to register twice
        }catch (Exception e){
            throw new EntityExistsException("This type of fuel already exists, try changing its price.");
        }
    }
    @Transactional
    public Fuel changePrice(PriceData priceData) throws EntityNotFoundException {
        return fuelRepo.save(priceDataService.saveData(priceData));
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
