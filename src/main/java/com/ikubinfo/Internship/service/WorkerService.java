package com.ikubinfo.Internship.service;

import com.ikubinfo.Internship.dto.BillDto;
import com.ikubinfo.Internship.dto.OrderDto;
import com.ikubinfo.Internship.entity.Fuel;
import com.ikubinfo.Internship.entity.Order;
import com.ikubinfo.Internship.entity.Worker;
import com.ikubinfo.Internship.repository.OrderRepo;
import com.ikubinfo.Internship.repository.WorkerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class WorkerService {
    private final WorkerRepo workerRepo;
    private final OrderService orderService;
    private final FuelService fuelService;
    private final OrderRepo orderRepo;

    @Autowired
    public WorkerService(WorkerRepo workerRepo, OrderService orderService, FuelService fuelService, OrderRepo orderRepo) {
        this.workerRepo = workerRepo;
        this.orderService = orderService;
        this.fuelService = fuelService;
        this.orderRepo = orderRepo;
    }

    public Worker registerWorker(Worker newWorker) throws EntityExistsException {
        if (workerRepo.existsById(newWorker.getId())) {
            throw new EntityExistsException("Worker already exists");
        }
        return workerRepo.save(newWorker);
    }

    @Transactional
    public BillDto processOrder(Long workerId, OrderDto orderDto) throws PersistenceException {
        Fuel fuel = fuelService.getFuel(orderDto.getFuelType());
        Worker worker = workerRepo.getById(workerId);
        if (fuel == null) {
            throw new PersistenceException("Order not processed. No such fuel type found");
        } else if (worker == null) {
            throw new PersistenceException("Order not processed. Worker not found");
        }
        Double total = fuelService.buyFuel(fuel, orderDto.getAmount());        //decreases the available amount from deposit, returns amountBought* currentPrice, throws exc if not enough amount
        Order order = orderRepo.save(new Order(fuel, orderDto.getAmount(), worker, total));
        worker.setShiftBalance(worker.getShiftBalance() + total);
        return new BillDto(order);
    }

    public Double getShiftBalance(Long workerId) throws EntityNotFoundException {
        Worker worker = workerRepo.getById(workerId);
        if (worker == null) {
            throw new EntityNotFoundException("Worker not found");
        }
        return worker.getShiftBalance();
    }

    public List<Object[]> getShiftBalanceHistory(Long workerId) {
        Worker worker = workerRepo.getById(workerId);
        if (worker == null) {
            throw new EntityNotFoundException("Worker not found");
        }
        return orderService.getWorkerBalanceHistory(workerId);
    }

    public List<Worker> getWorkers() {
        return StreamSupport.stream(workerRepo.findAll().spliterator(), false)     //converting from iterable to list
                .collect(Collectors.toList());
    }

    public List<Worker> getWorkersOfAdmin(Long adminId) {
        return workerRepo.getAllByAdmin_Id(adminId);
    }

    public Worker getWorker(Long adminId, Long workerId) {
        return workerRepo.getByAdmin_IdAndId(adminId, workerId);
    }

    public Worker getWorkerByName(String name) {
        return workerRepo.getByName(name);
    }

    public Worker updateWorker(Worker worker) throws EntityNotFoundException {
        if (!workerRepo.existsById(worker.getId())) {
            throw new EntityNotFoundException("Worker not found");
        }
        return workerRepo.save(worker);
    }

    public void deleteWorker(Long workerId) {
        workerRepo.deleteById(workerId);
    }

    public void deleteAllWorkers(Long adminId) {
        workerRepo.deleteAllByAdmin_Id(adminId);
    }
}
