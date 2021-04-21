package com.ikubinfo.Internship.service;

import com.ikubinfo.Internship.dto.BillDto;
import com.ikubinfo.Internship.dto.OrderDto;
import com.ikubinfo.Internship.dto.WorkerDto;
import com.ikubinfo.Internship.entity.Admin;
import com.ikubinfo.Internship.entity.Fuel;
import com.ikubinfo.Internship.entity.Order;
import com.ikubinfo.Internship.entity.Worker;
import com.ikubinfo.Internship.repository.AdminRepo;
import com.ikubinfo.Internship.repository.OrderRepo;
import com.ikubinfo.Internship.repository.WorkerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WorkerService {
    private final WorkerRepo workerRepo;
    private final OrderService orderService;
    private final FuelService fuelService;
    private final OrderRepo orderRepo;
    private final AdminRepo adminRepo;

    @Autowired
    public WorkerService(WorkerRepo workerRepo, OrderService orderService, FuelService fuelService, OrderRepo orderRepo, AdminRepo adminRepo) {
        this.workerRepo = workerRepo;
        this.orderService = orderService;
        this.fuelService = fuelService;
        this.orderRepo = orderRepo;
        this.adminRepo = adminRepo;
    }

    public Worker registerWorker(WorkerDto newWorker) throws EntityExistsException {
        Admin admin = adminRepo.getByName(newWorker.getAdministratedBy());
        if(admin == null){
            throw new EntityNotFoundException("Admin does not exist");
        }else if (workerRepo.existsByName(newWorker.getName())) {
            throw new EntityExistsException("Worker already exists");
        }
        Worker worker = WorkerDto.dtoToWorker(newWorker);
        worker.setAdmin(admin);
        return workerRepo.save(worker);
    }

    public List<Worker> getWorkersOfAdmin(String adminName) {
        return workerRepo.getAllByAdmin_Name(adminName);
    }

    public Worker getWorker(String workerName) {
        return workerRepo.getByName(workerName);
    }

    public Worker updateWorker(WorkerDto workerDto) throws EntityNotFoundException {
        Admin admin = adminRepo.getByName(workerDto.getName());
        if(admin == null){
            throw new EntityNotFoundException("Admin not found");
        }else if (!workerRepo.existsByName(workerDto.getName())) {
            throw new EntityNotFoundException("Worker not found");
        }
        Worker worker = WorkerDto.dtoToWorker(workerDto);
        worker.setAdmin(admin);
        return workerRepo.save(worker);
    }

    public void deleteWorker(String workerName) {
        workerRepo.deleteByName(workerName);
    }

    public void deleteAllWorkers(String adminName) {
        workerRepo.getAllByAdmin_Name(adminName);
    }

    public Map<String, Double> getWorkersBalances() {
        List<Worker> workerList = workerRepo.findAll();
        Map<String, Double> workersBalances = new HashMap<>();
        for (Worker w : workerList) {
            workersBalances.put(w.getName(), w.getShiftBalance());
        }
        return workersBalances;
    }

    @Transactional
    public BillDto processOrder(String workerName, OrderDto orderDto) throws PersistenceException {
        Fuel fuel = fuelService.getFuel(orderDto.getFuelType());
        Worker worker = workerRepo.getByName(workerName);
        if (fuel == null) {
            throw new PersistenceException("Order not processed. No such fuel type found");
        } else if (worker == null) {
            throw new PersistenceException("Order not processed. Worker not found");
        }
        Double total = fuelService.buyFuel(fuel, orderDto.getAmount());        //decreases the available amount from deposit, returns amountBought* currentPrice, throws exc if not enough amount
        Order order = orderRepo.save(new Order(fuel, orderDto.getAmount(), worker, total, LocalDateTime.now()));
        worker.setShiftBalance(worker.getShiftBalance() + total);
        return new BillDto(order);
    }

    public Double getShiftBalance(String workerName) throws EntityNotFoundException {
        Worker worker = workerRepo.getByName(workerName);
        if (worker == null) {
            throw new EntityNotFoundException("Worker not found");
        }
        return worker.getShiftBalance();
    }

    public List<Object[]> getShiftBalanceHistory(String workerName) {
        Worker worker = workerRepo.getByName(workerName);
        if (worker == null) {
            throw new EntityNotFoundException("Worker not found");
        }
        return orderService.getWorkerBalanceHistory(worker.getId());
    }
}
