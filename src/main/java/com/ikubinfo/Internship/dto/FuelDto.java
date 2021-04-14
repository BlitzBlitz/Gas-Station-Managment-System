package com.ikubinfo.Internship.dto;

import com.ikubinfo.Internship.entity.Fuel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FuelDto {
    @NotNull
    private Long id;

    @NotNull
    private String type;

    @NotNull
    @Min(value = 0,message = "Can`t accept negative price")
    private Double currentPrice;

    @NotNull
    @Min(value = 0,message = "Can`t accept negative amount")
    private Double currentAvailableAmount;


    public static FuelDto entityToDto(Fuel fuel){
        return new FuelDto(
                fuel.getId(), fuel.getType(), fuel.getCurrentPrice(), fuel.getCurrentAvailableAmount()
        );
    }

    public static List<FuelDto> entityToDto(List<Fuel> fuels){
        return fuels.stream().map(fuel -> entityToDto(fuel)).collect(Collectors.toList());
    }

    public static Fuel dtoToEntity(FuelDto fuelDto){
        return new Fuel(
                fuelDto.getId(),fuelDto.getType(),fuelDto.getCurrentPrice(),fuelDto.getCurrentAvailableAmount()
        );
    }

    public static List<Fuel> dtoToEntity(List<FuelDto> fuelDtos){
        return fuelDtos.stream().map(fuelDto -> dtoToEntity(fuelDto)).collect(Collectors.toList());
    }
}
