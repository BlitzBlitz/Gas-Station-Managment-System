package com.ikubinfo.Internship.controller;


import com.ikubinfo.Internship.dto.BillDto;
import com.ikubinfo.Internship.dto.OrderDto;
import com.ikubinfo.Internship.dto.WorkerDto;
import com.ikubinfo.Internship.entity.Worker;
import com.ikubinfo.Internship.service.WorkerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("workers")
public class WorkerController {
    private final WorkerService workerService;

    public WorkerController(WorkerService workerService) {
        this.workerService = workerService;
    }

    @GetMapping
    public ResponseEntity<List<WorkerDto>> getWorkers(@RequestParam String adminName){
        return new ResponseEntity<>(WorkerDto.entityToDto(workerService.getWorkersOfAdmin(adminName)),
                HttpStatus.OK);
    }
    @GetMapping("/{workerName}")
    public ResponseEntity<WorkerDto> getWorker(@PathVariable String workerName){
        return new ResponseEntity<>(WorkerDto.entityToDto(workerService.getWorker(workerName)),
                HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<WorkerDto> registerWorker(@Valid @RequestBody WorkerDto workerDto){
        Worker saved = workerService.registerWorker(workerDto);
        return new ResponseEntity<>(WorkerDto.entityToDto(saved), HttpStatus.CREATED);
    }
    @PutMapping("/{workerName}")
    public ResponseEntity<WorkerDto> updateWorker(@Valid @RequestBody WorkerDto workerDto){
        Worker updatedWorker = workerService.updateWorker(workerDto);
        return new ResponseEntity<>(WorkerDto.entityToDto(updatedWorker),
                HttpStatus.OK);
    }
    @DeleteMapping("/{workerName}")
    public void deleteWorker(@PathVariable String workerName){
        workerService.deleteWorker(workerName);
    }
    @DeleteMapping
    public void deleteAllWorkers(@RequestParam String adminName){
        workerService.deleteAllWorkers(adminName);
    }


    @GetMapping("/{workerName}/shiftBalance")
    public ResponseEntity<Double> getShiftBalance(@PathVariable String workerName){
        return new ResponseEntity<>(workerService.getShiftBalance(workerName), HttpStatus.OK);
    }
    @GetMapping("/{workerName}/shiftHistory")
    public ResponseEntity<List<Object[]>> getHistory(@PathVariable String workerName){
        return new ResponseEntity<>(workerService.getShiftBalanceHistory(workerName), HttpStatus.OK);
    }
    @PostMapping("/{workerName}/processOrder")
    public ResponseEntity<BillDto> processOrder(@PathVariable String workerName, @RequestBody OrderDto orderDto){
        return new ResponseEntity<>(workerService.processOrder(workerName, orderDto), HttpStatus.OK);
    }

    @GetMapping("/balances")
    public ResponseEntity<Map<String, Double>> getWorkersBalances() {
        return new ResponseEntity<>(workerService.getWorkersBalances(), HttpStatus.OK);
    }
}
