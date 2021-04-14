package com.ikubinfo.Internship.dto;

import com.ikubinfo.Internship.entity.PriceData;
import com.ikubinfo.Internship.service.AdminService;
import com.ikubinfo.Internship.service.FuelService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class PriceDataDto {

    private static AdminService adminService;
    private static FuelService fuelService;

    @Autowired
    private PriceDataDto(AdminService adminService, FuelService fuelService){
        PriceDataDto.adminService = adminService;
        PriceDataDto.fuelService = fuelService;
    }

    @NotNull
    private Long id;

    @NotNull
    @Min(value = 0, message = "Negative price value")
    private Double price;

    @NotNull
    @Size(min = 2, message = "Fuel type should be longer than 2 char")
    private String fuelType;

    @NotNull
    private Long changedBy;

    public static PriceData dtoToEntity(PriceDataDto priceDataDto){
        return new PriceData(priceDataDto.getId(), priceDataDto.getPrice(), fuelService.getFuel(priceDataDto.getFuelType()),
                adminService.getAdmin(priceDataDto.getChangedBy()), new Date());
    }
    public static List<PriceData> dtoToEntity(List<PriceDataDto> priceDataDtos){
        return priceDataDtos.stream().map(priceDataDto -> dtoToEntity(priceDataDto)).collect(Collectors.toList());
    }

    public static PriceDataDto entityToDto(PriceData priceData){
        return new PriceDataDto(priceData.getId(), priceData.getPrice(), priceData.getFuelType().getType(),
                priceData.getChangedBy().getId());
    }
    public static List<PriceDataDto> entityToDto(List<PriceData> priceDataList){
        return priceDataList.stream().map(priceData -> entityToDto(priceData)).collect(Collectors.toList());
    }



}
