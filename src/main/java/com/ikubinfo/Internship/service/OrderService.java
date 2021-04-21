package com.ikubinfo.Internship.service;

import com.ikubinfo.Internship.repository.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepo orderRepo;
    private final FuelService fuelService;

    @Autowired
    public OrderService(OrderRepo orderRepo, FuelService fuelService) {
        this.orderRepo = orderRepo;
        this.fuelService = fuelService;
    }

    public List<Object[]> getYearlyStatistics(int year) {
        return orderRepo.getAllMonthlyTotalsOrderByTotal(year);
    }

    public List<Object[]> getWorkerBalanceHistory(Long id) {
        return orderRepo.findWorkerBalanceHistory(id);
    }

    public Integer getNumberOfOrdersBy(String getBy, LocalDate date) {
        if(getBy.equalsIgnoreCase("day")){
            return orderRepo.countByOrderDate_Date(date);
        }else if(getBy.equalsIgnoreCase("month")){
            return orderRepo.countByOrderDate_Month(date.getYear(), date.getMonthValue());
        }else if(getBy.equalsIgnoreCase("year")){
            return orderRepo.countByOrderDate_Year(date.getYear());
        }
        throw new EntityNotFoundException("Wrong filter! Must be one of the following: day, month, year");
    }

    public Double getTotalBy(String getBy, LocalDate date) {
        if(getBy.equalsIgnoreCase("day")){
            return orderRepo.getTotalOfDay(date);
        }else if(getBy.equalsIgnoreCase("month")){
            return orderRepo.getTotalOfMonth(date.getYear(), date.getMonthValue());
        }else if(getBy.equalsIgnoreCase("year")){
            return orderRepo.getTotalOfYear(date.getYear());
        }
        throw new EntityNotFoundException("Wrong filter! Must be one of the following: day, month, year");
    }
}
