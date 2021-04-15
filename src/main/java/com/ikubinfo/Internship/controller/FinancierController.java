package com.ikubinfo.Internship.controller;

import com.ikubinfo.Internship.service.FinancierService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.Map;

@Controller
@RequestMapping("/financiers/{financierId}")
public class FinancierController {

    public final FinancierService financierService;

    public FinancierController(FinancierService financierService) {
        this.financierService = financierService;
    }

    @GetMapping("/workers")
    public ResponseEntity<Map<String, Double>> getWorkersBalances() {
        return new ResponseEntity<>(financierService.getWorkersBalances(), HttpStatus.OK);
    }

    @GetMapping("/statistics/{year}")
    public ResponseEntity<Map<LocalDate, Double>> getYearlyStatistics(@PathVariable Integer year) {
        return new ResponseEntity<>(financierService.getYearlyStatistics(year), HttpStatus.OK);
    }

    @GetMapping("/statistics/{year}/total")
    public ResponseEntity<Double> getYearTotal(@PathVariable Integer year) {
        return new ResponseEntity<>(financierService.getTotalOfYear(year), HttpStatus.OK);
    }
    @GetMapping("/statistics/{year}/{month}/total")
    public ResponseEntity<Double> getMonthTotal(@PathVariable Integer year, @PathVariable Integer month) {
        return new ResponseEntity<>(financierService.getTotalOfMonth(year, month), HttpStatus.OK);
    }
    @GetMapping("/statistics/{year}/{month}/{day}/total")
    public ResponseEntity<Double> getDayTotal(@PathVariable Integer year, @PathVariable Integer month,@PathVariable Integer day) {
        return new ResponseEntity<>(financierService.getTotalOfDay(year, month, day), HttpStatus.OK);
    }
    @GetMapping("/statistics/{year}/{month}/{day}/numberOfOrders")
    public ResponseEntity<Integer> getDayNumberOfOrders(@PathVariable Integer year, @PathVariable Integer month,@PathVariable Integer day) {
        return new ResponseEntity<>(financierService.getNumberOfOrdersInADay(year, month, day), HttpStatus.OK);
    }
}
