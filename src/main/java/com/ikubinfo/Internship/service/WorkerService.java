package com.ikubinfo.Internship.service;

import com.ikubinfo.Internship.dto.BillDto;
import com.ikubinfo.Internship.dto.OrderDto;
import com.ikubinfo.Internship.dto.UserDto;
import com.ikubinfo.Internship.dto.WorkerDto;
import com.ikubinfo.Internship.entity.Fuel;
import com.ikubinfo.Internship.entity.Order;
import com.ikubinfo.Internship.entity.UserD;
import com.ikubinfo.Internship.entity.Worker;
import com.ikubinfo.Internship.exception.ExistsReqException;
import com.ikubinfo.Internship.exception.NotFoundReqException;
import com.ikubinfo.Internship.repository.OrderRepo;
import com.ikubinfo.Internship.repository.UserRepo;
import com.ikubinfo.Internship.repository.WorkerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private final RegistrationService registrationService;
    private final UserRepo userRepo;

    @Autowired
    public WorkerService(WorkerRepo workerRepo, OrderService orderService, FuelService fuelService,
                         OrderRepo orderRepo, RegistrationService registrationService, UserRepo userRepo) {
        this.workerRepo = workerRepo;
        this.orderService = orderService;
        this.fuelService = fuelService;
        this.orderRepo = orderRepo;
        this.registrationService = registrationService;
        this.userRepo = userRepo;
    }
    @Transactional
    public Worker registerWorker(WorkerDto workerDto) throws EntityExistsException {
        if (workerRepo.existsByWorkerDetails_Username(workerDto.getName())) {        //exists
            throw new ExistsReqException("Worker already exists");
        }

        Worker oldWorker = workerRepo.getFromHistory(workerDto.getName());                  //exists in history
        if(oldWorker != null){
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            oldWorker.setDeleted(false);
            oldWorker.getWorkerDetails().setPassword(passwordEncoder.encode(workerDto.getPassword()));
            return workerRepo.save(oldWorker);
        }

        Worker worker = WorkerDto.dtoToWorker(workerDto);                                   //new
        UserD userD = registrationService.registerUser(new UserDto(
                workerDto.getName(), workerDto.getPassword(), "WORKER"
        ));
        worker.setWorkerDetails(userD);
        return workerRepo.save(worker);
    }

    public List<Worker> getWorkers() {
        List<Worker> all = workerRepo.findAll();
        if(all.isEmpty()){
            throw new NotFoundReqException("No workers found");
        }
        return all;
    }

    public Worker getWorker(String workerName) {
        Worker worker = workerRepo.getByWorkerDetails_Username(workerName);
        if(worker == null){
            throw new NotFoundReqException("Worker not found");
        }
        return worker;
    }
    @Transactional
    public Worker updateWorker(WorkerDto workerDto) throws EntityNotFoundException {
        if (!workerRepo.existsByWorkerDetails_Username(workerDto.getName())) {
            throw new NotFoundReqException("Worker not found");
        }
        Worker worker = WorkerDto.dtoToWorker(workerDto);
        UserD userD = userRepo.getByUsername(workerDto.getName());
        userD.setPassword(workerDto.getPassword());
        worker.setWorkerDetails(userD);
        return workerRepo.save(worker);
    }
    @Transactional
    public void deleteWorker(String workerName) {
        Integer result = workerRepo.deleteByWorkerDetails_Username(workerName);
        if(result == 0){
            throw new NotFoundReqException("Worker does not exist");
        }
    }


    public Map<String, Double> getWorkersBalances() {
        List<Worker> workerList = workerRepo.findAll();
        Map<String, Double> workersBalances = new HashMap<>();
        for (Worker w : workerList) {
            workersBalances.put(w.getWorkerDetails().getUsername(), w.getShiftBalance());
        }
        return workersBalances;
    }

    @Transactional
    public BillDto processOrder(String workerName, OrderDto orderDto) throws PersistenceException {
        Fuel fuel = fuelService.getFuel(orderDto.getFuelType());
        Worker worker = workerRepo.getByWorkerDetails_Username(workerName);
        if (fuel == null) {
            throw new NotFoundReqException("Order not processed. No such fuel type found");
        } else if (worker == null) {
            throw new NotFoundReqException("Order not processed. Worker not found");
        }
        Double total = fuelService.buyFuel(fuel, orderDto.getAmount());        //decreases the available amount from deposit, returns amountBought* currentPrice, throws exc if not enough amount
        Order order = orderRepo.save(new Order(fuel, orderDto.getAmount(), worker, total, LocalDateTime.now()));
        worker.setShiftBalance(worker.getShiftBalance() + total);
        return new BillDto(order);
    }

    public Double getShiftBalance(String workerName) throws EntityNotFoundException {
        Worker worker = workerRepo.getByWorkerDetails_Username(workerName);
        if (worker == null) {
            throw new NotFoundReqException("Worker not found");
        }
        return worker.getShiftBalance();
    }

    public List<Object[]> getShiftBalanceHistory(String workerName) {
        Worker worker = workerRepo.getByWorkerDetails_Username(workerName);
        if (worker == null) {
            throw new NotFoundReqException("Worker not found");
        }
        return orderService.getWorkerBalanceHistory(worker.getId());
    }
}
