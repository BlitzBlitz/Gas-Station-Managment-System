package com.ikubinfo.Internship.controller;

import com.ikubinfo.Internship.dto.FinancierDto;
import com.ikubinfo.Internship.entity.Financier;
import com.ikubinfo.Internship.service.FinancierService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/financiers")
public class FinancierController {

    public final FinancierService financierService;

    public FinancierController(FinancierService financierService) {
        this.financierService = financierService;
    }


    @GetMapping("/{financierName}")
    public ResponseEntity<FinancierDto> getFinancier(@PathVariable String financierName){
        return new ResponseEntity<FinancierDto>(FinancierDto.entityToDto(financierService.getFinancier(financierName)),
                HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<FinancierDto> registerFinancier(@Valid @RequestBody FinancierDto financierDto){
        Financier saved = financierService.registerFinancier(financierDto);
        return new ResponseEntity<>(FinancierDto.entityToDto(saved), HttpStatus.CREATED);
    }
    @PutMapping("/{financierName}")
    public ResponseEntity<FinancierDto> updateFinancier(@Valid @RequestBody FinancierDto financierDto){
        Financier updatedFinancier = financierService.updateFinancier(financierDto);
        return new ResponseEntity<>(FinancierDto.entityToDto(updatedFinancier),
                HttpStatus.OK);
    }
    @DeleteMapping("/{financierName}")
    public ResponseEntity<Integer> deleteFinancier(@PathVariable String financierName){

        return new ResponseEntity<>(financierService.deleteFinancierByName(financierName),
                HttpStatus.OK);
    }

    @PostMapping("/{financierName}/invest")
    public ResponseEntity<Double> invest(@RequestBody Double investmentAmount, @PathVariable String financierName){
        return new ResponseEntity(financierService.invest(investmentAmount, financierName), HttpStatus.OK);
    }
    @PutMapping("/{financierName}/collectDailyTotal")
    public ResponseEntity<Double> collectDailyTotal(@PathVariable String financierName){
        return new ResponseEntity(financierService.getShiftPayments(financierName), HttpStatus.OK);
    }
}
