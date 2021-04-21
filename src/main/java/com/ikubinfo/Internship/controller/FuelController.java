package com.ikubinfo.Internship.controller;

import com.ikubinfo.Internship.dto.FuelDto;
import com.ikubinfo.Internship.dto.FuelSupplyDataDto;
import com.ikubinfo.Internship.dto.PriceDataDto;
import com.ikubinfo.Internship.entity.Fuel;
import com.ikubinfo.Internship.service.FuelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("fuels")
public class FuelController {
    private final FuelService fuelService;

    public FuelController(FuelService fuelService) {
        this.fuelService = fuelService;
    }

    @GetMapping
    public ResponseEntity<List<FuelDto>> getAllFuels(){
        return new ResponseEntity<>(FuelDto.entityToDto(fuelService.getAllFuels()), HttpStatus.OK);
    }
    @GetMapping("/{fuelType}")
    public ResponseEntity<FuelDto> getFuel(@PathVariable String fuelType){
        return new ResponseEntity<>(FuelDto.entityToDto(fuelService.getFuel(fuelType)), HttpStatus.OK);
    }
    @GetMapping("/{fuelType}/history")
    public ResponseEntity<List<PriceDataDto>> getFuelHistory(@PathVariable String fuelType){
        return new ResponseEntity<>(PriceDataDto.entityToDto(fuelService.getFuelPriceHistory(fuelType)), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<FuelDto> addFuel(@Valid @RequestBody FuelDto fuelDto){
        Fuel saved = fuelService.addFuel(fuelDto);
        return new ResponseEntity<>(FuelDto.entityToDto(saved), HttpStatus.OK);
    }
    @PutMapping("/{fuelType}")
    public ResponseEntity<FuelDto> changePrice(@Valid @RequestBody PriceDataDto priceDataDto){
        Fuel updated = fuelService.changePrice(priceDataDto);
        return new ResponseEntity<>(FuelDto.entityToDto(updated), HttpStatus.OK);
    }
    @DeleteMapping
    public void deleteFuel(@RequestParam String fuelType){
        fuelService.deleteFuel(fuelType);
    }


    @PostMapping("/{fuelType}")
    public ResponseEntity<FuelSupplyDataDto> addFuelSupply(@Valid @RequestBody FuelSupplyDataDto fuelSupplyDataDto) {
        return new ResponseEntity<>(FuelSupplyDataDto.entityToDto(fuelService.supplyFuel(fuelSupplyDataDto)), HttpStatus.OK);
    }
}
