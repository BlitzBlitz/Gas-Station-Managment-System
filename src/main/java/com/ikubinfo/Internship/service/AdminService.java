package com.ikubinfo.Internship.service;

import com.ikubinfo.Internship.dto.AdminDto;
import com.ikubinfo.Internship.dto.UserDto;
import com.ikubinfo.Internship.entity.Admin;
import com.ikubinfo.Internship.entity.User;
import com.ikubinfo.Internship.exception.ExistsReqException;
import com.ikubinfo.Internship.exception.NotFoundReqException;
import com.ikubinfo.Internship.repository.AdminRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AdminService {
    private final AdminRepo adminRepo;
    private final RegistrationService registrationService;
    //TODO // is it a better way to inject this?
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public AdminService(AdminRepo adminRepo, RegistrationService registrationService) {
        this.adminRepo = adminRepo;
        this.registrationService = registrationService;
    }

    public List<Admin> getAllAdmins() {
        return StreamSupport.stream(adminRepo.findAll().spliterator(), false)     //converting from iterable to list
                .collect(Collectors.toList());
    }

    public Admin getAdmin(String adminName){
        if (!adminRepo.existsByAdminDetails_Username(adminName)) {
            throw new NotFoundReqException("Admin not found");
        }
        return adminRepo.getByAdminDetails_Username(adminName);
    }

    public Admin registerAdmin(UserDto userDto) throws EntityExistsException {
        if(adminRepo.existsByAdminDetails_Username(userDto.getUsername())){            //exists
            throw new ExistsReqException("Admin already exists");
        }
        Admin oldAdmin = adminRepo.getFromHistory(userDto.getUsername());                  //exists in history(disabled)
        if(oldAdmin != null){
            oldAdmin.setDeleted(false);                                                     //enable
            oldAdmin.getAdminDetails().setPassword(passwordEncoder.encode(userDto.getPassword()));
            return adminRepo.save(oldAdmin);
        }
        User adminDetails = registrationService.registerUser(userDto);                  //new
        Admin admin = new Admin();
        admin.setAdminDetails(adminDetails);
        return adminRepo.save(admin);
    }

    public Admin updateAdmin(AdminDto adminDto) throws EntityNotFoundException {
        if (!adminRepo.existsByAdminDetails_Username(adminDto.getName())) {
            throw new NotFoundReqException("Admin does not exist");
        }
        Admin admin = adminRepo.getByAdminDetails_Username(adminDto.getName());
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        admin.getAdminDetails().setPassword(passwordEncoder.encode(adminDto.getPassword()));
        return adminRepo.save(admin);
    }

    @Transactional
    public int deleteAdmin(String name) {
        return adminRepo.deleteByAdminDetails_Username(name);
    }


}
