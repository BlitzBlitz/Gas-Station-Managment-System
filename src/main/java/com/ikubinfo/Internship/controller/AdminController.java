package com.ikubinfo.Internship.controller;

import com.ikubinfo.Internship.dto.AdminDto;
import com.ikubinfo.Internship.entity.Admin;
import com.ikubinfo.Internship.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AdminDto>> getAdmins(){
        List<Admin> adminList = adminService.getAllAdmins();
        return new ResponseEntity<>(AdminDto.entityToDto(adminList), HttpStatus.OK);
    }
    @GetMapping("/{adminName}")
    @PreAuthorize("hasRole('ADMIN')")
    public  ResponseEntity<AdminDto> getAdmin(@PathVariable String adminName){
        Admin admin = adminService.getAdmin(adminName);
        return new ResponseEntity<>(AdminDto.entityToDto(admin), HttpStatus.OK);
    }

    @PutMapping("/{adminName}")
    @PreAuthorize("hasRole('ADMIN')")
    public  ResponseEntity<AdminDto> updateAdmin(@Valid @RequestBody AdminDto adminDto){
        Admin admin = adminService.updateAdmin(adminDto);
        return new ResponseEntity<>(AdminDto.entityToDto(admin), HttpStatus.OK);
    }
    @DeleteMapping("/{adminName}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteAdmin(@PathVariable String adminName){
        adminService.deleteAdmin(adminName);
    }

}
