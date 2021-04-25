package com.ikubinfo.Internship.service;

import com.ikubinfo.Internship.dto.AdminDto;
import com.ikubinfo.Internship.dto.UserDto;
import com.ikubinfo.Internship.entity.Admin;
import com.ikubinfo.Internship.entity.User;
import com.ikubinfo.Internship.repository.AdminRepo;
import com.ikubinfo.Internship.repository.UserRepo;
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
    private final UserRepo userRepo;
    private final RegistrationService registrationService;

    @Autowired
    public AdminService(AdminRepo adminRepo, UserRepo userRepo, RegistrationService registrationService) {
        this.adminRepo = adminRepo;
        this.userRepo = userRepo;
        this.registrationService = registrationService;
    }


    public List<Admin> getAllAdmins() {
        return StreamSupport.stream(adminRepo.findAll().spliterator(), false)     //converting from iterable to list
                .collect(Collectors.toList());
    }

    public Admin getAdmin(String adminName) throws EntityNotFoundException {
        if (!adminRepo.existsByAdminDetails_Username(adminName)) {
            throw new EntityNotFoundException("Admin not found");
        }
        return adminRepo.getByAdminDetails_Username(adminName);
    }

    public Admin getAdminByName(String name) {
        return adminRepo.getByAdminDetails_Username(name);
    }

    public Admin registerAdmin(UserDto userDto) throws EntityExistsException {
        if(adminRepo.existsByAdminDetails_Username(userDto.getUsername())){            //exists
            throw new EntityExistsException("Admin already exists");
        }
        Admin oldAdmin = adminRepo.getFromHistory(userDto.getUsername());                  //exists in history
        if(oldAdmin != null){
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            oldAdmin.setDeleted(false);
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
            throw new EntityNotFoundException("Admin does not exist");
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
