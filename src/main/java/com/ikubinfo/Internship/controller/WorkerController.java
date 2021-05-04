package com.ikubinfo.Internship.controller;


import com.ikubinfo.Internship.dto.BillDto;
import com.ikubinfo.Internship.dto.OrderDto;
import com.ikubinfo.Internship.dto.WorkerDto;
import com.ikubinfo.Internship.entity.Worker;
import com.ikubinfo.Internship.service.WorkerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("workers")
public class WorkerController {
    private final WorkerService workerService;

    public WorkerController(WorkerService workerService) {
        this.workerService = workerService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<WorkerDto>> getWorkers(){
        return new ResponseEntity<>(WorkerDto.entityToDto(workerService.getWorkers()),
                HttpStatus.OK);
    }
    @GetMapping("/{workerName}")
    @PreAuthorize("hasAnyRole('ADMIN', 'WORKER')")
    public ResponseEntity<WorkerDto> getWorker(@PathVariable String workerName){
        return new ResponseEntity<>(WorkerDto.entityToDto(workerService.getWorker(workerName)),
                HttpStatus.OK);
    }
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<WorkerDto> registerWorker(@Valid @RequestBody WorkerDto workerDto){
        Worker saved = workerService.registerWorker(workerDto);
        return new ResponseEntity<>(WorkerDto.entityToDto(saved), HttpStatus.CREATED);
    }
    @PutMapping("/{workerName}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<WorkerDto> updateWorker(@Valid @RequestBody WorkerDto workerDto){
        Worker updatedWorker = workerService.updateWorker(workerDto);
        return new ResponseEntity<>(WorkerDto.entityToDto(updatedWorker),
                HttpStatus.OK);
    }
    @DeleteMapping("/{workerName}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteWorker(@PathVariable String workerName){
        workerService.deleteWorker(workerName);
    }

    @GetMapping("/{workerName}/shiftBalance")
    @PreAuthorize("hasRole('WORKER')")
    public ResponseEntity<Double> getShiftBalance(@PathVariable String workerName){
        return new ResponseEntity<>(workerService.getShiftBalance(workerName), HttpStatus.OK);
    }
    @GetMapping("/{workerName}/shiftHistory")
    @PreAuthorize("hasAnyRole('ADMIN', 'WORKER')")
    public ResponseEntity<List<Object[]>> getShiftsHistory(@PathVariable String workerName){
        return new ResponseEntity<>(workerService.getShiftBalanceHistory(workerName), HttpStatus.OK);
    }
    @PostMapping("/{workerName}/processOrder")
    @PreAuthorize("hasRole('WORKER')")
    public ResponseEntity<BillDto> processOrder(@PathVariable String workerName, @RequestBody OrderDto orderDto){
        return new ResponseEntity<>(workerService.processOrder(workerName, orderDto), HttpStatus.OK);
    }

    @GetMapping("/balances")
    @PreAuthorize("hasRole('FINANCIER')")
    public ResponseEntity<Map<String, Double>> getWorkersBalances() {
        return new ResponseEntity<>(workerService.getWorkersBalances(), HttpStatus.OK);
    }
}
