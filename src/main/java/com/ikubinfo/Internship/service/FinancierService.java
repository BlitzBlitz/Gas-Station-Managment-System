package com.ikubinfo.Internship.service;

import com.ikubinfo.Internship.entity.Financier;
import com.ikubinfo.Internship.entity.Worker;
import com.ikubinfo.Internship.repository.*;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class FinancierService {

    private final FinancierRepo financierRepo;
    private final WorkerRepo workerRepo;

    public FinancierService(FinancierRepo financierRepo, WorkerRepo workerRepo) {
        this.financierRepo = financierRepo;
        this.workerRepo = workerRepo;
    }


    public Financier getFinancier(String financierName) {
        return financierRepo.getByUsername(financierName);
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

    public void deleteFinancierByName(String financierName) {
        financierRepo.deleteByUsername(financierName);
    }

    public void deleteAllFinanciers() {
        financierRepo.deleteAll();
    }

    public Double invest(Double amount, String financierName) {
        Financier financier = financierRepo.getByUsername(financierName);
        if (financier == null) {
            throw new EntityNotFoundException("Financier not found");
        } else if (amount < 100) {
            throw new PersistenceException("Minimal investment amount is 100$");
        }
        Double currentBalance = financier.invest(amount);
        financierRepo.save(financier);
        return currentBalance;
    }

    @Transactional
    public Double getShiftPayments(String financierName) {                      //returns currentGasStationBalance
        Financier financier = financierRepo.getByUsername(financierName);
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


}
