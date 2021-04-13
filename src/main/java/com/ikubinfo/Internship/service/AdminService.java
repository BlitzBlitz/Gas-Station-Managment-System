package com.ikubinfo.Internship.service;

import com.ikubinfo.Internship.entity.Admin;
import com.ikubinfo.Internship.entity.Fuel;
import com.ikubinfo.Internship.entity.PriceData;
import com.ikubinfo.Internship.entity.Worker;
import com.ikubinfo.Internship.repository.AdminRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AdminService {
    private FuelService fuelService;
    private WorkerService workerService;
    private AdminRepo adminRepo;

    @Autowired
    public AdminService(FuelService fuelService, WorkerService workerService, AdminRepo adminRepo) {
        this.fuelService = fuelService;
        this.workerService = workerService;
        this.adminRepo = adminRepo;
    }

    public void registerFuelType(Fuel newFuelType)  {
        fuelService.addFuel(newFuelType);
    }
    public void changePrice(String fuelType, Double newPrice) throws EntityNotFoundException {
        fuelService.changePrice(fuelType, newPrice);
    }
    public void removeFuelType(String fuelType){
        fuelService.removeFuelType(fuelType);
    }

    public List<PriceData> getFuelPriceHistory(String fuelType){
        return fuelService.getFuelPriceHistory(fuelType);
    }
    public Worker registerWorker(Worker newWorker){
        return workerService.registerWorker(newWorker);
    }
    public Admin registerAdmin(Admin admin) throws EntityExistsException{
        if(adminRepo.existsById(admin.getId())){
            throw new EntityExistsException("Admin already exists");
        }
        return adminRepo.save(admin);
    }
    public List<Admin> getAllAdmins(){
        return StreamSupport.stream( adminRepo.findAll().spliterator(), false)     //converting from iterable to list
                .collect(Collectors.toList());
    }
    public void deleteAllAdmins(){
        adminRepo.deleteAll();
    }
    public Admin getAdmin(Long id){
        if(!adminRepo.existsById(id)){
            throw new EntityNotFoundException("Admin not found");
        }
        return adminRepo.findById(id).get();
    }
    public Admin updateAdmin(Admin admin) throws EntityNotFoundException{
        if(!adminRepo.existsById(admin.getId())){
            throw new EntityNotFoundException("Admin does not exist");
        }
        return adminRepo.save(admin);
    }
    public void deleteAdmin(Long id){
        adminRepo.deleteById(id);
    }
    public List<Worker> getAllWorkers(Long adminId){
        return workerService.getWorkersOfAdmin(adminId);
    }
    public Worker getWorker(Long adminId, Long workerId){
        return workerService.getWorker(adminId, workerId);
    }
    public Worker updateWorker(Worker worker){
        return workerService.updateWorker(worker);
    }
    public void deleteWorker(Long workerId){
        workerService.deleteWorker(workerId);
    }
    public void deleteAllWorkers(Long adminId){
        workerService.deleteAllWorkers(adminId);
    }
}
