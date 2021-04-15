package com.ikubinfo.Internship.dto;

import com.ikubinfo.Internship.entity.Order;
import com.ikubinfo.Internship.service.FuelService;
import com.ikubinfo.Internship.service.GasStationService;
import com.ikubinfo.Internship.service.WorkerService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class OrderDto {
    @Autowired
    private static FuelService fuelService;
    @Autowired
    private static GasStationService gasStationService;
    @Autowired
    private static WorkerService workerService;

    @NotNull
    private String fuelType;
    @NotNull
    private Long id;
    @NotNull
    @Min(value = 0, message = "Negative amount not allowed")
    private Double amount;
    @NotNull
    @Min(value = 0, message = "Negative amount not allowed")
    private Double total;
    @NotNull
    private String processedAt;
    @NotNull
    private String processedBy;


    public static Order dtoToEntity(OrderDto orderDto){
        return new Order(fuelService.getFuel(orderDto.getFuelType()), orderDto.getAmount(), workerService.getWorkerByName(orderDto.getProcessedBy()),
                orderDto.getTotal(), gasStationService.getGasStation(orderDto.getProcessedAt()));
    }
    public static List<Order> dtoToEntity(List<OrderDto> orderDtos){
        return orderDtos.stream().map(orderDto -> dtoToEntity(orderDto)).collect(Collectors.toList());
    }

    public static OrderDto entityToDto(Order order){
        return new OrderDto(order.getFuelType().getType(), order.getId(), order.getAmount(), order.getTotal(),
                order.getProcessedAt().getName(), order.getProcessedBy().getName());
    }
    public static List<OrderDto> entityToDto(List<Order> orders){
        return orders.stream().map(order -> entityToDto(order)).collect(Collectors.toList());
    }

}
