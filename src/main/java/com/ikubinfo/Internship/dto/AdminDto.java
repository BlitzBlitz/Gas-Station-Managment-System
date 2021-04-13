package com.ikubinfo.Internship.dto;

import com.ikubinfo.Internship.entity.Admin;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminDto {

    @NotNull
    private Long id;

    @NotEmpty
    @Size(min = 2, message = "username should be longer than 2 char")
    private String name;

    @NotEmpty
    @Size(min = 8, message = "password must have at least 8 char")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[`!@#$%^&+=])(?=\\S+$).{8,}$",
            message = "Password must be at least 8 char, have 1 number, 1 special char, no white spaces")
    private String password;


    public static AdminDto entityToDto(Admin admin){
        AdminDto adminDto = new AdminDto();
        adminDto.setId(admin.getId());
        adminDto.setName(admin.getName());
//        adminDto.setPassword(admin.getPassword());
        return adminDto;
    }

    public static List<AdminDto> entityToDto(List<Admin> admins){
        return admins.stream().map(admin -> entityToDto(admin)).collect(Collectors.toList());
    }

    public static Admin dtoToEntity(AdminDto adminDto){
        Admin admin = new Admin();
        admin.setId(adminDto.getId());
        admin.setName(adminDto.getName());
        admin.setPassword(adminDto.getPassword());
        return admin;
    }
    public static List<Admin> dtoToEntity(List<AdminDto> admins){
        return admins.stream().map(admin -> dtoToEntity(admin)).collect(Collectors.toList());
    }

}
