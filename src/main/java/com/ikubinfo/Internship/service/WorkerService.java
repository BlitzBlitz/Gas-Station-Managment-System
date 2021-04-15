package com.ikubinfo.Internship.service;

import com.ikubinfo.Internship.entity.Fuel;
import com.ikubinfo.Internship.entity.GasStation;
import com.ikubinfo.Internship.entity.Order;
import com.ikubinfo.Internship.entity.Worker;
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
    private WorkerRepo workerRepo;
    private OrderService orderService;
    private FuelService fuelService;
    private GasStationService gasStationService;


    @Autowired
    public WorkerService(WorkerRepo workerRepo, OrderService orderService, FuelService fuelService) {
        this.workerRepo = workerRepo;
        this.orderService = orderService;
        this.fuelService = fuelService;
    }

    public Worker registerWorker(Worker newWorker) throws EntityExistsException{
        if(workerRepo.existsById(newWorker.getId())){
            throw new EntityExistsException("Worker already exists");
        }

        return workerRepo.save(newWorker);
    }
    @Transactional
    public void processOrder(String fuelType, Double amount, String workerName, String gasSName) throws PersistenceException{
        Fuel fuel = fuelService.getFuel(fuelType);
        Worker worker = workerRepo.findByName(workerName);
        GasStation gasStation = gasStationService.getGasStation(gasSName);
        if(fuel == null){
            throw new PersistenceException("Order not processed. No such fuel type found");
        }else if(worker == null){
            throw new PersistenceException("Order not processed. Worker not found");
        }else if(gasStation == null){
            throw new PersistenceException("Order not processed. Gas station not found");
        }
        Double total = fuelService.buyFuel(fuel,amount);
        if(total == -1){                //not enough fuel available
            throw new PersistenceException("Order not processed. Not enough fuel available!");
        }
        orderService.saveOrder(new Order(fuel, amount, worker,total, gasStationService.getGasStation(gasSName)));
        worker.setShiftBalance(worker.getShiftBalance() + total);
    }

    public Double getShiftBalance(String workerName) throws EntityNotFoundException {
        Worker worker = workerRepo.findByName(workerName);
        if(worker == null){
            throw new EntityNotFoundException("Worker not found");
        }
        return worker.getShiftBalance();
    }
    public List<Object[]> getShiftBalanceHistory(String workerName){
        Worker worker = workerRepo.findByName(workerName);
        if(worker == null){
            throw new EntityNotFoundException("Worker not found");
        }
        return orderService.getWorkerBalanceHistory(worker.getId());
    }

    public List<Worker> getWorkers(){
        return StreamSupport.stream( workerRepo.findAll().spliterator(), false)     //converting from iterable to list
                        .collect(Collectors.toList());
    }

    public List<Worker> getWorkersOfAdmin(Long adminId){
        return StreamSupport.stream( workerRepo.findAllByAdmin_Id(adminId).spliterator(), false)     //converting from iterable to list
                .collect(Collectors.toList());
    }
    public Worker getWorker(Long adminId, Long workerId){
        return workerRepo.findByAdmin_IdAndId(adminId, workerId);
    }
    public Worker getWorkerByName(String name){
        return workerRepo.getByName(name);
    }
    public Worker updateWorker(Worker worker) throws EntityNotFoundException{
        if(!workerRepo.existsById(worker.getId())) {
            throw new EntityNotFoundException("Worker not found");
        }
        return workerRepo.save(worker);
    }
    public void deleteWorker(Long workerId){
        workerRepo.deleteById(workerId);
    }
    public void  deleteAllWorkers(Long adminId){
        workerRepo.deleteAllByAdmin_Id(adminId);
    }
}
