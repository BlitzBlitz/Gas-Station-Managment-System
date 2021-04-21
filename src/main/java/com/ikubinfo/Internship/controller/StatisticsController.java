package com.ikubinfo.Internship.controller;

import com.ikubinfo.Internship.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/statistics")
public class StatisticsController {

    private final OrderService orderService;

    public StatisticsController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/year")
    public ResponseEntity<List<Object[]>> getYearlyStatistics(@RequestParam Integer year) {
        return new ResponseEntity<>(orderService.getYearlyStatistics(year), HttpStatus.OK);
    }

    @GetMapping("/total")
    public ResponseEntity<Double> getTotal(@RequestParam String getBy, @RequestBody LocalDate date) {
        return new ResponseEntity<>(orderService.getTotalBy(getBy, date), HttpStatus.OK);
    }

    @GetMapping("/numberOfOrders")
    public ResponseEntity<Integer> getDayNumberOfOrders(@RequestParam String getBy, @RequestBody LocalDate date) {
        return new ResponseEntity<>(orderService.getNumberOfOrdersBy(getBy, date), HttpStatus.OK);
    }
}
