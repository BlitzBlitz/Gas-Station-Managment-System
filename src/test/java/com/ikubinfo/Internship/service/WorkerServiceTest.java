package com.ikubinfo.Internship.service;

import com.ikubinfo.Internship.dto.OrderDto;
import com.ikubinfo.Internship.entity.*;
import com.ikubinfo.Internship.repository.OrderRepo;
import com.ikubinfo.Internship.repository.UserRepo;
import com.ikubinfo.Internship.repository.WorkerRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@WebMvcTest(WorkerService.class)
@ActiveProfiles(value = "test")
public class WorkerServiceTest {
    @Autowired
    WorkerService workerService;
    @MockBean
    WorkerRepo workerRepo;
    @MockBean
    OrderService orderService;
    @MockBean
    FuelService fuelService;
    @MockBean
    OrderRepo orderRepo;
    @MockBean
    RegistrationService registrationService;
    @MockBean
    UserRepo userRepo;

    @Test
    public void processOrderTest() {
        Fuel fuel = new Fuel("gas", 120.0, new FuelDeposit(20.0));
        OrderDto orderDto = new OrderDto("gas", 10.0);
        Worker worker = new Worker(1000.0, 200.0, new UserD("beni", "ecenVet"));
        Mockito.when(fuelService.getFuel("gas")).thenReturn(fuel);
        Mockito.when(workerRepo.getByWorkerDetails_Username("beni")).thenReturn(worker);
        Order order = new Order(fuel,  orderDto.getAmount(), worker,
                fuelService.buyFuel(fuel, 10.0), LocalDateTime.now());
        Mockito.when(orderRepo.save(Mockito.any())).thenReturn(order);
        System.out.println(workerService.processOrder("beni", orderDto));
    }
}
