package com.ikubinfo.Internship.dto;

import com.ikubinfo.Internship.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillDto {
    private String fuelType;
    private Double amount;
    private Double total;
    private String processedBy;
    private LocalDateTime processAtTime;

    public BillDto(Order order){
        this.fuelType = order.getFuelType().getType();
        this.amount = order.getAmount();
        this.total = order.getTotal();
        this.processedBy = order.getProcessedBy().getWorkerDetails().getUsername();
        this.processAtTime = order.getOrderDate();
    }
}
