package com.ikubinfo.Internship.service;

import com.ikubinfo.Internship.dto.FinancierDto;
import com.ikubinfo.Internship.dto.UserDto;
import com.ikubinfo.Internship.entity.Financier;
import com.ikubinfo.Internship.entity.User;
import com.ikubinfo.Internship.entity.Worker;
import com.ikubinfo.Internship.exception.InvalidReqException;
import com.ikubinfo.Internship.exception.NotFoundReqException;
import com.ikubinfo.Internship.repository.FinancierRepo;
import com.ikubinfo.Internship.repository.WorkerRepo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class FinancierService {

    private final FinancierRepo financierRepo;
    private final WorkerRepo workerRepo;
    private final RegistrationService registrationService;

    public FinancierService(FinancierRepo financierRepo, WorkerRepo workerRepo, RegistrationService registrationService) {
        this.financierRepo = financierRepo;
        this.workerRepo = workerRepo;
        this.registrationService = registrationService;
    }


    public Financier getFinancier(String financierName) {
        Financier financier = financierRepo.getByFinancierDetails_Username(financierName);
        if (financier == null) {
            throw new NotFoundReqException("Financier not found");
        }
        return financier;
    }

    @Transactional
    public Financier registerFinancier(FinancierDto financierDto) {
        if (financierRepo.existsByFinancierDetails_Username(financierDto.getUsername())) {                      //exists
            throw new EntityExistsException("Financier already exists");
        }
        Financier oldFinancier = financierRepo.getFromHistory(financierDto.getUsername());
        if (oldFinancier != null) {                                                                           //exists in history
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            oldFinancier.setDeleted(false);
            oldFinancier.getFinancierDetails().setPassword(passwordEncoder.encode(financierDto.getPassword()));
            oldFinancier.setSalary(financierDto.getSalary());
            return financierRepo.save(oldFinancier);
        }
        User financierDetails = registrationService.registerUser(new UserDto(
                financierDto.getUsername(), financierDto.getPassword(), "FINANCIER"
        ));
        Financier financier = new Financier();
        financier.setFinancierDetails(financierDetails);
        financier.setSalary(financierDto.getSalary());
        return financierRepo.save(financier);
    }

    @Transactional
    public Financier updateFinancier(FinancierDto financierDto) {
        if (!financierRepo.existsByFinancierDetails_Username(financierDto.getUsername())) {
            throw new NotFoundReqException("Financier does not exists");
        }
        Financier financier = financierRepo.getByFinancierDetails_Username(financierDto.getUsername());
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        financier.getFinancierDetails().setPassword(passwordEncoder.encode(financierDto.getPassword()));
        financier.setSalary(financierDto.getSalary());
        return financierRepo.save(financier);
    }

    @Transactional
    public int deleteFinancierByName(String financierName) {
        return financierRepo.deleteByFinancierDetails_Username(financierName);
    }

    @Transactional
    public Double invest(Double amount, String financierName) {
        Financier financier = financierRepo.getByFinancierDetails_Username(financierName);
        if (financier == null) {
            throw new NotFoundReqException("Financier not found");
        } else if (amount < 100) {
            throw new InvalidReqException("Minimal investment amount is 100$");
        }
        Double currentBalance = financier.invest(amount);
        financierRepo.save(financier);
        return currentBalance;
    }

    @Transactional
    public Double getShiftPayments(String financierName) {                      //returns currentGasStationBalance
        Financier financier = financierRepo.getByFinancierDetails_Username(financierName);
        if (financier == null) {
            throw new NotFoundReqException("Financier not found");
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
