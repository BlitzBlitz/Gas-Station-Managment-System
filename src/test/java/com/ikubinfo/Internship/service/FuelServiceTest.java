package com.ikubinfo.Internship.service;

import com.ikubinfo.Internship.dto.FuelSupplyDataDto;
import com.ikubinfo.Internship.entity.*;
import com.ikubinfo.Internship.repository.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@WebMvcTest(FuelService.class)
@ActiveProfiles(value = "test")
public class FuelServiceTest {
    @Autowired
    FuelService fuelService;
    @MockBean
    FuelRepo fuelRepo;
    @MockBean
    PriceDataRepo priceDataRepo;
    @MockBean
    AdminRepo adminRepo;
    @MockBean
    FuelDepositRepo fuelDepositRepo;
    @MockBean
    FinancierRepo financierRepo;
    @MockBean
    FuelSupplyDataRepo fuelSupplyDataRepo;

    @Test
    public void buyFuelTest() {
        Fuel fuel = new Fuel("gas", 120.0, new FuelDeposit(20.0));
        assertEquals((Double) 1200.0, fuelService.buyFuel(fuel, 10.0));
    }

    @Test
    public void supplyFuel() throws Exception{
        Financier financier = new Financier(1L, 2000.0, 2000.0, false,
                new UserD("miri", "ecenVet"));
        Mockito.when(financierRepo.getByFinancierDetails_Username("miri")).thenReturn(financier);
        Fuel fuel = new Fuel("gas", 120.0, new FuelDeposit(20.0));
        Mockito.when(fuelRepo.getByType("gas")).thenReturn(fuel);

        FuelSupplyData fuelSupplyData = fuelService.supplyFuel(
                new FuelSupplyDataDto("gas", 120.0, 10.0, "miri"));

        assertEquals((Double)10.0, fuelSupplyData.getSuppliedAmount());
    }
}
