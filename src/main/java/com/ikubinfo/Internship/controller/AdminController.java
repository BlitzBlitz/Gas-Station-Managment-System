package com.ikubinfo.Internship.controller;

import com.ikubinfo.Internship.dto.*;
import com.ikubinfo.Internship.entity.Admin;
import com.ikubinfo.Internship.entity.Financier;
import com.ikubinfo.Internship.entity.Fuel;
import com.ikubinfo.Internship.entity.Worker;
import com.ikubinfo.Internship.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/admins")
public class AdminController {
    private AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }



    //Admins
    @GetMapping
    public ResponseEntity<List<AdminDto>> getAdmins(){
        List<Admin> adminList = adminService.getAllAdmins();
        return new ResponseEntity<List<AdminDto>>(AdminDto.entityToDto(adminList), HttpStatus.OK);
    }
    @GetMapping("/{adminId}")
    public  ResponseEntity<AdminDto> getAdmin(@PathVariable long adminId){
        Admin admin = adminService.getAdmin(adminId);
        return new ResponseEntity<AdminDto>(AdminDto.entityToDto(admin), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<AdminDto> registerAdmin(@Valid @RequestBody AdminDto adminDto){
        Admin saved = adminService.registerAdmin(AdminDto.dtoToEntity(adminDto));
        return new ResponseEntity<AdminDto>(AdminDto.entityToDto(saved), HttpStatus.CREATED);
    }
    @PutMapping("/{adminId}")
    public  ResponseEntity<AdminDto> updateAdmin(@Valid @RequestBody AdminDto adminDto){
        Admin admin = adminService.updateAdmin(AdminDto.dtoToEntity(adminDto));
        return new ResponseEntity<AdminDto>(AdminDto.entityToDto(admin), HttpStatus.OK);
    }
    @DeleteMapping("/{adminId}")
    public void deleteAdmin(@PathVariable Long adminId){
        adminService.deleteAdmin(adminId);
    }
    @DeleteMapping
    public void deleteAdmins(){
        adminService.deleteAllAdmins();
    }

    //Fuels
    @GetMapping("/{adminId}/fuels")
    public ResponseEntity<List<FuelDto>> getAllFuels(){
        return new ResponseEntity<List<FuelDto>>(FuelDto.entityToDto(adminService.getAllFuels()), HttpStatus.OK);
    }
    @GetMapping("/{adminId}/fuels/{fuelType}")
    public ResponseEntity<FuelDto> getAllFuels(@PathVariable String fuelType){
        return new ResponseEntity<>(FuelDto.entityToDto(adminService.getFuel(fuelType)), HttpStatus.OK);
    }
    @PostMapping("/{adminId}/fuels")
    public ResponseEntity<FuelDto> addFuel(@Valid @RequestBody FuelDto fuelDto){
        Fuel saved = adminService.addFuel(FuelDto.dtoToEntity(fuelDto));
        return new ResponseEntity<>(FuelDto.entityToDto(saved), HttpStatus.OK);
    }
    @PutMapping("/{adminId}/fuels/{fuelType}")
    public ResponseEntity<FuelDto> changePrice(@Valid @RequestBody PriceDataDto priceDataDto){
        Fuel updated = adminService.changePrice(PriceDataDto.dtoToEntity(priceDataDto));
        return new ResponseEntity<FuelDto>(FuelDto.entityToDto(updated), HttpStatus.OK);
    }
    @DeleteMapping("/{adminId}/fuels/{fuelType}")
    public void deleteFuel(@PathVariable String fuelType){
        adminService.deleteFuel(fuelType);
    }

    //Workers
    @GetMapping("/{adminId}/workers")
    public ResponseEntity<List<WorkerDto>> getWorkers(@PathVariable Long adminId){
        return new ResponseEntity<List<WorkerDto>>(WorkerDto.entityToDto(adminService.getAllWorkers(adminId)),
                HttpStatus.OK);
    }
    @GetMapping("/{adminId}/workers/{workerId}")
    public ResponseEntity<WorkerDto> getWorker(@PathVariable Long adminId, @PathVariable Long workerId){
        return new ResponseEntity<WorkerDto>(WorkerDto.entityToDto(adminService.getWorker(adminId, workerId)),
                HttpStatus.OK);
    }
    @PostMapping("/{adminId}/workers")
    public ResponseEntity<WorkerDto> registerWorker(@Valid @RequestBody WorkerDto workerDto, @PathVariable long adminId){
        Worker saved = adminService.registerWorker(WorkerDto.dtoToWorker(workerDto), adminId);
        return new ResponseEntity<WorkerDto>(WorkerDto.entityToDto(saved), HttpStatus.CREATED);
    }
    @PutMapping("/{adminId}/workers/{workerId}")
    public ResponseEntity<WorkerDto> updateWorker(@PathVariable Long adminId, @Valid @RequestBody WorkerDto workerDto){
        Worker updatedWorker = adminService.updateWorker(WorkerDto.dtoToWorker(workerDto), adminId);
        return new ResponseEntity<WorkerDto>(WorkerDto.entityToDto(updatedWorker),
                HttpStatus.OK);
    }
    @DeleteMapping("/{adminId}/workers/{workerId}")
    public void deleteWorker(@PathVariable Long workerId){
        adminService.deleteWorker(workerId);
    }
    @DeleteMapping("/{adminId}/workers")
    public void deleteAllWorkers(@PathVariable Long adminId){
        adminService.deleteAllWorkers(adminId);
    }

    //Financiers
    @GetMapping("/{adminId}/financiers")
    public ResponseEntity<List<FinancierDto>> getAllFinanciers(@PathVariable Long adminId){
        return new ResponseEntity<List<FinancierDto>>(FinancierDto.entityToDto(adminService.getAllFinanciers(adminId)),
                HttpStatus.OK);
    }
    @GetMapping("/{adminId}/financiers/{financierId}")
    public ResponseEntity<FinancierDto> getFinancier(@PathVariable Long adminId, @PathVariable Long financierId){
        return new ResponseEntity<FinancierDto>(FinancierDto.entityToDto(adminService.getFinancier(adminId, financierId)),
                HttpStatus.OK);
    }
    @PostMapping("/{adminId}/financiers")
    public ResponseEntity<FinancierDto> registerWorker(@Valid @RequestBody FinancierDto financierDto, @PathVariable Long adminId){
        Financier saved = adminService.registerFinancier(FinancierDto.dtoToEntity(financierDto), adminId);
        return new ResponseEntity<FinancierDto>(FinancierDto.entityToDto(saved), HttpStatus.CREATED);
    }
    @PutMapping("/{adminId}/financiers/{financierId}")
    public ResponseEntity<FinancierDto> updateFinancier(@PathVariable Long adminId, @Valid @RequestBody FinancierDto financierDto){
        Financier updatedFinancier = adminService.updateFinancier(FinancierDto.dtoToEntity(financierDto), adminId);
        return new ResponseEntity<FinancierDto>(FinancierDto.entityToDto(updatedFinancier),
                HttpStatus.OK);
    }
    @DeleteMapping("/{adminId}/financiers/{financierId}")
    public void deleteFinancier(@PathVariable Long financierId){
        adminService.deleteFinancier(financierId);
    }
    @DeleteMapping("/{adminId}/financiers")
    public void deleteAllFinanciers(@PathVariable Long adminId){
        adminService.deleteAllFinanciers(adminId);
    }



}
