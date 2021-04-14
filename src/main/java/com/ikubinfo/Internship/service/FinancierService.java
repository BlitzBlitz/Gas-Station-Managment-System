package com.ikubinfo.Internship.service;

import com.ikubinfo.Internship.entity.Financier;
import com.ikubinfo.Internship.entity.Worker;
import com.ikubinfo.Internship.repository.FinancierRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FinancierService {

    private WorkerService workerService;
    private OrderService orderService;
    private FinancierRepo financierRepo;

    @Autowired
    public FinancierService(WorkerService workerService,OrderService orderService) {
        this.workerService = workerService;
        this.orderService = orderService;
    }

    public List<Financier> getFinanciersOfAdmin(Long adminId){
        return financierRepo.getAllByAdmin_Id(adminId);
    }
    public Financier getFinancier(Long adminId, Long financierId){
        return financierRepo.getByAdmin_IdAndId(adminId,financierId);
    }
    public Financier registerFinancier(Financier financier){
        if(financierRepo.existsById(financier.getId())){
            throw new EntityExistsException("Financier already exists");
        }
        return financierRepo.save(financier);
    }
    public Financier updateFinancier(Financier financier){
        if(!financierRepo.existsById(financier.getId())){
            throw new EntityNotFoundException("Financier does not exists");
        }
        return financierRepo.save(financier);
    }
    public void deleteFinancier(Long financierId){
        financierRepo.deleteById(financierId);
    }
    public void deleteAllFinanciers(Long adminId){
        financierRepo.deleteAllByAdmin_Id(adminId);
    }




    public Map<String, Double> getWorkersBalances(){
        List<Worker> workerList = workerService.getWorkers();
        Map<String, Double> workersBalances = new HashMap<>();
        for(Worker w: workerList){
            workersBalances.put(w.getName(), w.getShiftBalance());
        }
        return workersBalances;
    }

    public Map<LocalDate, Double> getYearlyStatistics(int year){
        return orderService.getYearlyStatistics(year);
    }

    public Integer getNumberOfOrdersInADay(LocalDate date){
        return orderService.getOrdersForToday(date);
    }

    public Double getTotalOfDay(LocalDate date){
        return orderService.getTotalOfDay(date);
    }
    public Double getTotalOfMonth(int year, int month){
        return orderService.getTotalOfMonth(year, month);
    }
    public Double getTotalOfYear(int year){
        return orderService.getTotalOfYear(year);
    }

}
