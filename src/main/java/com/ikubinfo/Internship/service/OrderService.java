package com.ikubinfo.Internship.service;

import com.ikubinfo.Internship.exception.NotFoundReqException;
import com.ikubinfo.Internship.repository.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepo orderRepo;

    @Autowired
    public OrderService(OrderRepo orderRepo) {
        this.orderRepo = orderRepo;
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
        throw new NotFoundReqException("Wrong filter! Must be one of the following: day, month, year");
    }

    public Double getTotalBy(String getBy, LocalDate date) {
        if(getBy.equalsIgnoreCase("day")){
            return orderRepo.getTotalOfDay(date);
        }else if(getBy.equalsIgnoreCase("month")){
            return orderRepo.getTotalOfMonth(date.getYear(), date.getMonthValue());
        }else if(getBy.equalsIgnoreCase("year")){
            return orderRepo.getTotalOfYear(date.getYear());
        }
        throw new NotFoundReqException("Wrong filter! Must be one of the following: day, month, year");
    }
}
