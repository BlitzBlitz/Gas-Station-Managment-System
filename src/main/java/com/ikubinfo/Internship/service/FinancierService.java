package com.ikubinfo.Internship.service;

import com.ikubinfo.Internship.dto.FuelSupplyDataDto;
import com.ikubinfo.Internship.entity.Financier;
import com.ikubinfo.Internship.entity.Fuel;
import com.ikubinfo.Internship.entity.FuelSupplyData;
import com.ikubinfo.Internship.entity.Worker;
import com.ikubinfo.Internship.repository.FinancierRepo;
import com.ikubinfo.Internship.repository.FuelRepo;
import com.ikubinfo.Internship.repository.FuelSupplyDataRepo;
import com.ikubinfo.Internship.repository.WorkerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FinancierService {

    private final WorkerService workerService;
    private final OrderService orderService;
    private final FinancierRepo financierRepo;
    private final FuelRepo fuelRepo;
    private final FuelSupplyDataRepo fuelSupplyDataRepo;
    private final WorkerRepo workerRepo;

    @Autowired
    public FinancierService(WorkerService workerService, OrderService orderService, FinancierRepo financierRepo, FuelRepo fuelRepo, FuelSupplyDataRepo fuelSupplyDataRepo, WorkerRepo workerRepo) {
        this.workerService = workerService;
        this.orderService = orderService;
        this.financierRepo = financierRepo;
        this.fuelRepo = fuelRepo;
        this.fuelSupplyDataRepo = fuelSupplyDataRepo;
        this.workerRepo = workerRepo;
    }

    public List<Financier> getFinanciersOfAdmin(Long adminId) {
        return financierRepo.getAllByAdmin_Id(adminId);
    }

    public Financier getFinancier(Long adminId, Long financierId) {
        return financierRepo.getByAdmin_IdAndId(adminId, financierId);
    }

    public Financier registerFinancier(Financier financier) {
        if (financierRepo.existsById(financier.getId())) {
            throw new EntityExistsException("Financier already exists");
        }
        return financierRepo.save(financier);
    }

    public Financier updateFinancier(Financier financier) {
        if (!financierRepo.existsById(financier.getId())) {
            throw new EntityNotFoundException("Financier does not exists");
        }
        return financierRepo.save(financier);
    }

    public void deleteFinancier(Long financierId) {
        financierRepo.deleteById(financierId);
    }

    public void deleteAllFinanciers(Long adminId) {
        financierRepo.deleteAllByAdmin_Id(adminId);
    }

    //Fuels & Gas Station
    @Transactional
    public FuelSupplyData supplyFuel(FuelSupplyDataDto fuelSupplyDataDto) {
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

    public Double invest(Double amount, Long financierId) {
        Financier financier = financierRepo.getById(financierId);
        if (financier == null) {
            throw new EntityNotFoundException("Financier not found");
        } else if (amount < 100) {
            throw new PersistenceException("Minimal investment amount is 100$");
        }
        Double currentBalance = financier.invest(amount);
        financierRepo.save(financier);
        return currentBalance;
    }

    //Workers
    public Map<String, Double> getWorkersBalances() {
        List<Worker> workerList = workerService.getWorkers();
        Map<String, Double> workersBalances = new HashMap<>();
        for (Worker w : workerList) {
            workersBalances.put(w.getName(), w.getShiftBalance());
        }
        return workersBalances;
    }

    @Transactional
    public Double getShiftPayments(Long financierId) {                      //returns currentGasStationBalance
        Financier financier = financierRepo.getById(financierId);
        if(financier == null){
            throw new EntityNotFoundException("Financier not found");
        }
        Double dailyTotal = 0.0;
        List<Worker> workers = workerRepo.findAll();
        for (Worker worker : workers) {
            dailyTotal += worker.getShiftBalance();
            worker.setShiftBalance(0.0);
            workerRepo.save(worker);
        }
        financier.collectShiftPayments(dailyTotal);                     //adds dailyTotal to currentBalance
        financierRepo.save(financier);
        return financier.getGasStationBalance();
    }

    //Statistics
    public Map<LocalDate, Double> getYearlyStatistics(int year) {
        return orderService.getYearlyStatistics(year);
    }

    public Integer getNumberOfOrdersInADay(int year, int month, int day) {
        return orderService.getOrdersForToday(LocalDate.of(year, month, day));
    }

    public Double getTotalOfDay(int year, int month, int day) {
        return orderService.getTotalOfDay(LocalDate.of(year, month, day));
    }

    public Double getTotalOfMonth(int year, int month) {
        return orderService.getTotalOfMonth(year, month);
    }

    public Double getTotalOfYear(int year) {
        return orderService.getTotalOfYear(year);
    }

}
