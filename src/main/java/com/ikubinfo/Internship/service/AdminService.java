package com.ikubinfo.Internship.service;

import com.ikubinfo.Internship.entity.Admin;
import com.ikubinfo.Internship.repository.AdminRepo;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AdminService {
    private final AdminRepo adminRepo;

    public AdminService(AdminRepo adminRepo) {
        this.adminRepo = adminRepo;
    }


    public List<Admin> getAllAdmins() {
        return StreamSupport.stream(adminRepo.findAll().spliterator(), false)     //converting from iterable to list
                .collect(Collectors.toList());
    }

    public Admin getAdmin(Long id) throws EntityNotFoundException {
        if (!adminRepo.existsById(id)) {
            throw new EntityNotFoundException("Admin not found");
        }
        return adminRepo.findById(id).get();
    }

    public Admin getAdminByName(String name) {
        return adminRepo.getByName(name);
    }

    public Admin registerAdmin(Admin admin) throws EntityExistsException {
        if (adminRepo.existsById(admin.getId())) {
            throw new EntityExistsException("Admin already exists");
        }
        return adminRepo.save(admin);
    }

    public Admin updateAdmin(Admin admin) throws EntityNotFoundException {
        if (!adminRepo.existsById(admin.getId())) {
            throw new EntityNotFoundException("Admin does not exist");
        }
        return adminRepo.save(admin);
    }

    public void deleteAdmin(Long id) {
        adminRepo.deleteById(id);
    }

    public void deleteAllAdmins() {
        adminRepo.deleteAll();
    }

}
