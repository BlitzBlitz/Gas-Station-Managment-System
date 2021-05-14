package com.ikubinfo.Internship.service;

import com.ikubinfo.Internship.dto.FinancierDto;
import com.ikubinfo.Internship.entity.Financier;
import com.ikubinfo.Internship.entity.UserD;
import com.ikubinfo.Internship.exception.ExistsReqException;
import com.ikubinfo.Internship.repository.FinancierRepo;
import com.ikubinfo.Internship.repository.WorkerRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@WebMvcTest(FinancierService.class )
@ActiveProfiles(value = "test")
public class FinancierServiceTest {
    @Autowired
    FinancierService financierService;

    @MockBean
    private  FinancierRepo financierRepo;
    @MockBean
    private  WorkerRepo workerRepo;
    @MockBean
    private  RegistrationService registrationService;

    @Test
    public void getFinancierTest(){
        Financier financier = new Financier(1L, 2000.0, 30000.0,false,
                new UserD("miri", "ecenVet"));
        Mockito.when(financierRepo.getByFinancierDetails_Username("miri")).thenReturn(financier);
        assertEquals(financier, financierService.getFinancier("miri"));
    }

    @Test
    public void registerFinancierTestCase1() {               //exist
        Financier financier = new Financier(1L, 2000.0, 10000.0, false,
                new UserD("miri", "ecenVet"));
        Mockito.when(financierRepo.existsByFinancierDetails_Username("miri")).thenReturn(true);
        Exception exception = assertThrows(ExistsReqException.class, () -> financierService.registerFinancier(
                new FinancierDto("miri", "ecenVet", 2000.0)));
        assertTrue(exception.getMessage().equals("Financier already exists"));
    }

//    @Test
//    public void registerFinancierTestCase2() {           //disabled
//        Financier financier = new Financier(1L, 2000.0, 10000.0, false,
//                new User("miri", "ecenVet"));
//        Financier oldFinancier = new Financier(1L, 2000.0, 10000.0, true,
//                new User("miri", "VetEcen"));
//        Mockito.when(financierRepo.existsByFinancierDetails_Username("miri")).thenReturn(false);
//        Mockito.when(financierRepo.getFromHistory("miri")).thenReturn(oldFinancier);
//        Mockito.when(financierRepo.save(oldFinancier)).thenReturn(admin);
//        assertEquals(admin, adminService.registerAdmin(
//                new UserDto("beni", "ecenVet", "ADMIN")));
//    }
//
//    @Test
//    public void registerAdminTestCase3() {           //new
//        User adminDetails = new User("beni", "ecenVet");
//        Admin admin = new Admin(1L, LocalDateTime.now(), LocalDateTime.now(), false,
//                adminDetails, null);
//        UserDto userDto = new UserDto("beni", "ecenVet", "ADMIN");
//        Mockito.when(adminRepo.existsByAdminDetails_Username("beni")).thenReturn(false);
//        Mockito.when(adminRepo.getFromHistory("beni")).thenReturn(null);
//        Mockito.when(registrationService.registerUser(userDto)).thenReturn(adminDetails);
//        Mockito.when(adminRepo.save(Mockito.any())).thenReturn(admin);
//        assertEquals(admin, adminService.registerAdmin(userDto));
//    }
}
