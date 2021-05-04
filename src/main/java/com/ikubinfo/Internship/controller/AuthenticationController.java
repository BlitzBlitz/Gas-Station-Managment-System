package com.ikubinfo.Internship.controller;

import com.ikubinfo.Internship.dto.AdminDto;
import com.ikubinfo.Internship.dto.UserDto;
import com.ikubinfo.Internship.entity.Admin;
import com.ikubinfo.Internship.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AuthenticationController {
    private final AdminService adminService;

    public AuthenticationController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/register")
    public ResponseEntity<AdminDto> registerAdmin(@Valid @RequestBody UserDto userDto){
        Admin saved = adminService.registerAdmin(userDto);
        return new ResponseEntity<>(AdminDto.entityToDto(saved), HttpStatus.CREATED);
    }
    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(){
        return new ResponseEntity<>("Authenticated successfully", HttpStatus.OK);
    }
}
