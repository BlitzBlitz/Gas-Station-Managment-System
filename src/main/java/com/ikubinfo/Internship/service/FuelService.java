package com.ikubinfo.Internship.service;

import com.ikubinfo.Internship.dto.FuelDto;
import com.ikubinfo.Internship.dto.FuelSupplyDataDto;
import com.ikubinfo.Internship.dto.PriceDataDto;
import com.ikubinfo.Internship.entity.*;
import com.ikubinfo.Internship.repository.*;
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
    private final FinancierRepo financierRepo;
    private final FuelSupplyDataRepo fuelSupplyDataRepo;

    @Autowired
    public FuelService(FuelRepo fuelRepo, PriceDataRepo priceDataRepo, AdminRepo adminRepo, FuelDepositRepo fuelDepositRepo, FinancierRepo financierRepo, FuelSupplyDataRepo fuelSupplyDataRepo) {
        this.fuelRepo = fuelRepo;
        this.priceDataRepo = priceDataRepo;
        this.adminRepo = adminRepo;
        this.fuelDepositRepo = fuelDepositRepo;
        this.financierRepo = financierRepo;
        this.fuelSupplyDataRepo = fuelSupplyDataRepo;
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
    public void deleteFuel(String fuelType) throws EntityNotFoundException {
        int result = fuelRepo.deleteByType(fuelType);
        if (result == 0) {
            throw new EntityNotFoundException("Fuel type does not exist!");
        }
    }

    @Transactional
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

    @Transactional
    public FuelSupplyData supplyFuel(FuelSupplyDataDto fuelSupplyDataDto) throws PersistenceException {
        Financier financier = financierRepo.getByUsername(fuelSupplyDataDto.getBoughtBy());
        Fuel fuel = fuelRepo.getByType(fuelSupplyDataDto.getFuelType());
        if (financier == null) {
            throw new EntityNotFoundException("Financier not found");
        } else if (fuel == null) {
            throw new EntityNotFoundException("Fuel does not exist");
        }
        Double totalSupplyPrice = fuelSupplyDataDto.getPrice() * fuelSupplyDataDto.getAmount();
        if (financier.getGasStationBalance() - totalSupplyPrice < 0) {
            throw new PersistenceException("Not enough balance, try investing in your gas station");
        }
        fuel.getFuelDeposit().addFuel(fuelSupplyDataDto.getAmount());
        fuelRepo.save(fuel);
        FuelSupplyData fuelSupplyData = new FuelSupplyData(fuelSupplyDataDto.getAmount(), fuelSupplyDataDto.getPrice(),
                fuel, financier);
        return fuelSupplyDataRepo.save(fuelSupplyData);
    }
}
