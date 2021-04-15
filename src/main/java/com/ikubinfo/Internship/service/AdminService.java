package com.ikubinfo.Internship.service;

import com.ikubinfo.Internship.entity.*;
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
    private FinancierService financierService;
    private AdminRepo adminRepo;

    @Autowired
    public AdminService(FuelService fuelService, WorkerService workerService, AdminRepo adminRepo, FinancierService financierService) {
        this.fuelService = fuelService;
        this.workerService = workerService;
        this.adminRepo = adminRepo;
        this.financierService = financierService;
    }

    public List<Fuel> getAllFuels(){
        return fuelService.getAllFuels();
    }
    public Fuel getFuel(String type){
        return fuelService.getFuel(type);
    }
    public Fuel addFuel(Fuel fuel){
        return fuelService.addFuel(fuel);
    }
    public Fuel changePrice(PriceData priceData) throws EntityNotFoundException {
        return fuelService.changePrice(priceData);
    }
    public void deleteFuel(String fuelType){
        fuelService.removeFuelType(fuelType);
    }
    public List<PriceData> getFuelPriceHistory(String fuelType){
        return fuelService.getFuelPriceHistory(fuelType);
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
    public Admin getAdmin(Long id) throws EntityNotFoundException{
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
    public Worker registerWorker(Worker newWorker, Long adminId){
        if(!adminRepo.existsById(adminId)){
            throw new EntityNotFoundException("Admin not found");
        }
        newWorker.setAdmin(adminRepo.getById(adminId));
        return workerService.registerWorker(newWorker);
    }
    public Worker updateWorker(Worker worker, Long adminId){
        Admin admin = adminRepo.findById(adminId).get();
        worker.setAdmin(admin);
        return workerService.updateWorker(worker);
    }
    public void deleteWorker(Long workerId){
        workerService.deleteWorker(workerId);
    }
    public void deleteAllWorkers(Long adminId){
        workerService.deleteAllWorkers(adminId);
    }

    public List<Financier> getAllFinanciers(Long adminId){
        return financierService.getFinanciersOfAdmin(adminId);
    }
    public Financier getFinancier(Long adminId, Long financierId){
        return financierService.getFinancier(adminId, financierId);
    }
    public Financier registerFinancier(Financier financier, Long adminId){
        if(!adminRepo.existsById(adminId)){
            throw new EntityNotFoundException("Admin not found");
        }
        financier.setAdmin(adminRepo.getById(adminId));
        return financierService.registerFinancier(financier);
    }
    public Financier updateFinancier(Financier financier, Long adminId){
        if(!adminRepo.existsById(adminId)){
            throw new EntityNotFoundException("Admin not found");
        }
        financier.setAdmin(adminRepo.getById(adminId));
        return financierService.updateFinancier(financier);
    }
    public void deleteFinancier(Long financierId){
        financierService.deleteFinancier(financierId);
    }
    public void deleteAllFinanciers(Long adminId){
        financierService.deleteAllFinanciers(adminId);
    }
}
