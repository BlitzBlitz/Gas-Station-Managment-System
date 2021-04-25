package com.ikubinfo.Internship.dto;

import com.ikubinfo.Internship.entity.Financier;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class FinancierDto {


    @NotNull
    @Size(min = 2, message = "username should be longer than 2 char")
    private String username;

    @NotNull
    @NotEmpty
    @Size(min = 8, message = "password must have at least 8 char")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[`!@#$%^&+=])(?=\\S+$).{8,}$",
            message = "Password must be at least 8 char, have 1 number, 1 special char, no white spaces")
    private String password;

    @NotNull
    private Double salary;


    public static FinancierDto entityToDto(Financier financier){
        return new FinancierDto(
                financier.getFinancierDetails().getUsername(), null, financier.getSalary()
        );
    }

    public static List<FinancierDto> entityToDto(List<Financier> financiers){
        return financiers.stream().map(financier -> entityToDto(financier)).collect(Collectors.toList());
    }

}
