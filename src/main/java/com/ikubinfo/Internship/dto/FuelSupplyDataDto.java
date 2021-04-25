package com.ikubinfo.Internship.dto;


import com.ikubinfo.Internship.entity.FuelSupplyData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class FuelSupplyDataDto {
    @NotNull
    private String fuelType;
    @Min(value = 1, message = "Price not allowed")
    private Double price;
    @Min(value = 1, message = "Amount not allowed")
    private Double amount;
    @NotNull
    private String boughtBy;

    public static FuelSupplyDataDto entityToDto(FuelSupplyData fuelSupplyData){
        FuelSupplyDataDto fuelSupplyDataDto = new FuelSupplyDataDto();
        fuelSupplyDataDto.fuelType = fuelSupplyData.getFuelType().getType();
        fuelSupplyDataDto.price = fuelSupplyData.getSuppliedPrice();
        fuelSupplyDataDto.amount = fuelSupplyData.getSuppliedAmount();
        fuelSupplyDataDto.boughtBy = fuelSupplyData.getBoughtByFinancier().getFinancierDetails().getUsername();
        return fuelSupplyDataDto;
    }
}
