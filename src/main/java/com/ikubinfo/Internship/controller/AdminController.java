package com.ikubinfo.Internship.controller;

import com.ikubinfo.Internship.dto.AdminDto;
import com.ikubinfo.Internship.entity.Admin;
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

}
