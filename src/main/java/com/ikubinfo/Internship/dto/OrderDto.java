package com.ikubinfo.Internship.dto;

import com.ikubinfo.Internship.entity.Order;
import com.ikubinfo.Internship.service.FuelService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    @Autowired
    private static FuelService fuelService;

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


    public static Order dtoToEntity(OrderDto orderDto){
        return new Order(fuelService.getFuel(orderDto.getFuelType()), orderDto.getId(), orderDto.getAmount(), orderDto.getTotal());
    }
    public static List<Order> dtoToEntity(List<OrderDto> orderDtos){
        return orderDtos.stream().map(orderDto -> dtoToEntity(orderDto)).collect(Collectors.toList());
    }

    public static OrderDto entityToDto(Order order){
        return new OrderDto(order.getFuelType().getType(), order.getId(), order.getAmount(), order.getTotal());
    }
    public static List<OrderDto> entityToDto(List<Order> orders){
        return orders.stream().map(order -> entityToDto(order)).collect(Collectors.toList());
    }

}
