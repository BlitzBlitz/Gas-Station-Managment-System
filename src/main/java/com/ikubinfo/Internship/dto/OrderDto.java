package com.ikubinfo.Internship.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    @NotNull
    private String fuelType;
    @NotNull
    @Min(value = 0, message = "Negative amount not allowed")
    private Double amount;

}
