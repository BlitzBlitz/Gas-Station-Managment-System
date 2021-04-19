package com.ikubinfo.Internship.service;

import com.ikubinfo.Internship.repository.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {
    private final OrderRepo orderRepo;
    private final FuelService fuelService;

    @Autowired
    public OrderService(OrderRepo orderRepo, FuelService fuelService) {
        this.orderRepo = orderRepo;
        this.fuelService = fuelService;
    }


    public List<Object[]> getWorkerBalanceHistory(long id) {
        return orderRepo.findWorkerBalanceHistory(id);
    }

    public Map<LocalDate, Double> getYearlyStatistics(int year) {
//        return orderRepo.getAllMonthlyTotalsOrderByTotal(year);
        return null;
    }

    public Integer getOrdersForToday(LocalDate date) {
        return orderRepo.countByOrderDate_Date(date);
    }

    public Double getTotalOfDay(LocalDate date) {
        return orderRepo.countTotalOfDay(date);
    }

    public Double getTotalOfMonth(int year, int month) {
        return (Double) orderRepo.countTotalOfMonth(year, month);
    }

    public Double getTotalOfYear(int year) {
        return (Double) orderRepo.countTotalOfYear(year);
    }

}
