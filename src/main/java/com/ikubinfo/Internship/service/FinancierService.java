package com.ikubinfo.Internship.service;

import com.ikubinfo.Internship.entity.Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FinancierService {

    private WorkerService workerService;
    private OrderService orderService;

    @Autowired
    public FinancierService(WorkerService workerService,OrderService orderService) {
        this.workerService = workerService;
        this.orderService = orderService;
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
