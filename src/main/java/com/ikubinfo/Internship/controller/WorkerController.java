package com.ikubinfo.Internship.controller;


import com.ikubinfo.Internship.dto.BillDto;
import com.ikubinfo.Internship.dto.OrderDto;
import com.ikubinfo.Internship.service.WorkerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("workers/{workerId}")
public class WorkerController {
    private final WorkerService workerService;

    public WorkerController(WorkerService workerService) {
        this.workerService = workerService;
    }

    @GetMapping("/shiftBalance")
    public ResponseEntity<Double> getShiftBalance(@PathVariable Long workerId){
        return new ResponseEntity<>(workerService.getShiftBalance(workerId), HttpStatus.OK);
    }
    @GetMapping("/shiftHistory")
    public ResponseEntity<List<Object[]>> getHistory(@PathVariable Long workerId){
        return new ResponseEntity<>(workerService.getShiftBalanceHistory(workerId), HttpStatus.OK);
    }
    @PostMapping("/processOrder")
    public ResponseEntity<BillDto> processOrder(@PathVariable Long workerId, @RequestBody OrderDto orderDto){
        return new ResponseEntity<>(workerService.processOrder(workerId, orderDto), HttpStatus.OK);
    }
}
