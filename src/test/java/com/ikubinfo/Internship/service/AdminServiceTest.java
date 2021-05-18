package com.ikubinfo.Internship.service;

import com.ikubinfo.Internship.dto.AdminDto;
import com.ikubinfo.Internship.dto.UserDto;
import com.ikubinfo.Internship.entity.Admin;
import com.ikubinfo.Internship.entity.UserD;
import com.ikubinfo.Internship.exception.ExistsReqException;
import com.ikubinfo.Internship.repository.AdminRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@WebMvcTest(AdminService.class)
@ActiveProfiles(value = "test")
public class AdminServiceTest {
    @Autowired
    AdminService adminService;

    @MockBean
    private AdminRepo adminRepo;
    @MockBean
    private RegistrationService registrationService;


    @Test
    public void getAllAdminsTest() {
        Admin admin = new Admin(1L, LocalDateTime.now(), LocalDateTime.now(), false,
                new UserD("beni", "ecenVet"), null);
        Mockito.when(adminRepo.findAll()).thenReturn(Arrays.asList(
                admin
        ));
        assertEquals(Arrays.asList(admin), adminService.getAllAdmins());
    }

    @Test
    public void getAdminTest() {
        Admin admin = new Admin(1L, LocalDateTime.now(), LocalDateTime.now(), false,
                new UserD("beni", "ecenVet"), null);
        Mockito.when(adminRepo.getByAdminDetails_Username("beni")).thenReturn(admin);
        Mockito.when(adminRepo.existsByAdminDetails_Username("beni")).thenReturn(true);
        assertEquals(admin, adminService.getAdmin("beni"));
    }

    @Test
    public void registerAdminTestCase1() {               //exist
        Admin admin = new Admin(1L, LocalDateTime.now(), LocalDateTime.now(), false,
                new UserD("beni", new BCryptPasswordEncoder().encode("ecenVet")), null);
        Mockito.when(adminRepo.existsByAdminDetails_Username("beni")).thenReturn(true);

        Exception exception = assertThrows(ExistsReqException.class, () -> adminService.registerAdmin(
                new UserDto("beni", "ecenVet", "ADMIN")));
        assertTrue(exception.getMessage().equals("Admin already exists"));
    }

//    @Test
//    public void registerAdminTestCase2() {           //disabled
//        Admin admin = new Admin(1L, LocalDateTime.now(), LocalDateTime.now(), false,
//                new User("beni", adminService.passwordEncoder.encode("ecenVet")), null);
//        System.out.println(admin);
//        Admin oldAdmin = new Admin(1L, LocalDateTime.now(), LocalDateTime.now(), true,
//                new User("beni", adminService.passwordEncoder.encode("VetEcen")), null);
//        System.out.println(oldAdmin);
//        Mockito.when(adminRepo.existsByAdminDetails_Username("beni")).thenReturn(false);
//        Mockito.when(adminRepo.getFromHistory("beni")).thenReturn(oldAdmin);
//        Mockito.when(adminRepo.save(admin)).thenReturn(admin);
//        assertEquals(admin, adminService.registerAdmin(
//                new UserDto("beni", "ecenVet", "ADMIN")));
//    }

    @Test
    public void registerAdminTestCase3() {           //new
        UserD adminDetails = new UserD("beni", "ecenVet");
        Admin admin = new Admin(1L, LocalDateTime.now(), LocalDateTime.now(), false,
                adminDetails, null);
        UserDto userDto = new UserDto("beni", "ecenVet", "ADMIN");
        Mockito.when(adminRepo.existsByAdminDetails_Username("beni")).thenReturn(false);
        Mockito.when(adminRepo.getFromHistory("beni")).thenReturn(null);
        Mockito.when(registrationService.registerUser(userDto)).thenReturn(adminDetails);
        Mockito.when(adminRepo.save(Mockito.any())).thenReturn(admin);
        assertEquals(admin, adminService.registerAdmin(userDto));
    }


    @Test
    public void updateAdminTest() {
        Admin admin = new Admin(1L, LocalDateTime.now(), LocalDateTime.now(), false,
                new UserD("beni", "ecenVet"), null);
        Mockito.when(adminRepo.getByAdminDetails_Username("beni")).thenReturn(admin);
        Mockito.when(adminRepo.existsByAdminDetails_Username("beni")).thenReturn(true);

        admin.getAdminDetails().setPassword("secenVet");                //update
        Mockito.when(adminRepo.save(admin)).thenReturn(admin);
        assertEquals(admin, adminService.updateAdmin(new AdminDto("beni", "secenVet")));
    }

    @Test
    public void deleteAdminTest() {
        Mockito.when(adminRepo.deleteByAdminDetails_Username("beni")).thenReturn(1);
        assertEquals(1, adminService.deleteAdmin("beni"));
    }
}
