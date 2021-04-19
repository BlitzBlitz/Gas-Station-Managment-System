package com.ikubinfo.Internship.service;

import com.ikubinfo.Internship.dto.FuelDto;
import com.ikubinfo.Internship.dto.PriceDataDto;
import com.ikubinfo.Internship.entity.Admin;
import com.ikubinfo.Internship.entity.Fuel;
import com.ikubinfo.Internship.entity.FuelDeposit;
import com.ikubinfo.Internship.entity.PriceData;
import com.ikubinfo.Internship.repository.AdminRepo;
import com.ikubinfo.Internship.repository.FuelDepositRepo;
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
    private final PriceDataRepo priceDataRepo;
    private final AdminRepo adminRepo;
    private final FuelDepositRepo fuelDepositRepo;

    @Autowired
    public FuelService(FuelRepo fuelRepo, PriceDataRepo priceDataRepo, AdminRepo adminRepo, FuelDepositRepo fuelDepositRepo) {
        this.fuelRepo = fuelRepo;
        this.priceDataRepo = priceDataRepo;
        this.adminRepo = adminRepo;
        this.fuelDepositRepo = fuelDepositRepo;
    }

    public List<Fuel> getAllFuels() {
        return fuelRepo.findAll();
    }

    public Fuel getFuel(String type) {
        return fuelRepo.getByType(type);
    }

    @Transactional
    public Fuel addFuel(FuelDto newFuel) throws EntityExistsException {
        if (fuelRepo.existsByType(newFuel.getType())) {
            throw new EntityExistsException("Fuel already exists");
        }
        FuelDeposit fuelDeposit = new FuelDeposit(newFuel.getCurrentAvailableAmount());                                 //new deposit
        fuelDepositRepo.save(fuelDeposit);                                                          //persist
        Fuel newFuelType = new Fuel(newFuel.getType(), newFuel.getCurrentPrice());                  //new fuel type
        newFuelType.setFuelDeposit(fuelDeposit);                                                    // set deposit
        return fuelRepo.save(newFuelType);                                                          //persist
    }

    @Transactional
    public Fuel changePrice(PriceDataDto priceDataDto) throws EntityNotFoundException {
        Fuel fuel = fuelRepo.getByType(priceDataDto.getFuelType());         //select fuel by name
        Admin admin = adminRepo.getByName(priceDataDto.getChangedBy());
        if (fuel == null) {
            throw new EntityNotFoundException("Fuel does not exist");
        } else if (admin == null) {
            throw new EntityNotFoundException("Admin does not exist");
        }
        priceDataRepo.save(new PriceData(fuel.getCurrentPrice(), fuel, admin));     //save priceData
        fuel.setCurrentPrice(priceDataDto.getPrice());                       //set new price
        return fuelRepo.save(fuel);                                             //update fuel
    }

    @Transactional
    public void removeFuelType(String fuelType) throws EntityNotFoundException {
        int result = fuelRepo.deleteByType(fuelType);
        if (result == 0) {
            throw new EntityNotFoundException("Fuel type does not exist!");
        }
    }

    @Transactional
    //https://www.netsurfingzone.com/hibernate/failed-to-lazily-initialize-a-collection-of-role-could-not-initialize-proxy-no-session/
    public List<PriceData> getFuelPriceHistory(String fuelType) throws EntityNotFoundException {
        Fuel fuel = fuelRepo.findByTypeForHistory(fuelType);                            //uses native query to surpass the is_delete filter
        List<PriceData> priceData = priceDataRepo.getAllByFuelType_Id(fuel.getId());    //uses native query to surpass the is_delete filter
        if (priceData == null) {
            throw new EntityNotFoundException("Fuel history found for fuel: " + fuelType);
        }
        return priceData;
    }

    @Transactional
    public Double buyFuel(Fuel fuel, Double buyingAmount) throws PersistenceException {
        Double availableAmount = fuel.getFuelDeposit().getAvailableAmount();
        if (availableAmount - buyingAmount >= 0) {
            fuel.getFuelDeposit().setAvailableAmount(availableAmount - buyingAmount);
            try {
                fuelRepo.save(fuel);
            } catch (Exception e) {
                throw new PersistenceException("Error buying fuel!");
            }
            return fuel.getCurrentPrice() * buyingAmount;
        } else {
            throw new PersistenceException("Available amount not enough");
        }
    }
}
