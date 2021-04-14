package com.ikubinfo.Internship.dto;

import com.ikubinfo.Internship.entity.Worker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkerDto {


    @NotNull
    private Long id;

    @NotNull
    @Size(min = 2, message = "username should be longer than 2 char")
    private String name;

    @NotNull
    @Size(min = 8, message = "password must have at least 8 char")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[`!@#$%^&+=])(?=\\S+$).{8,}$",
            message = "Password must be at least 8 char, have 1 number, 1 special char, no white spaces")
    private String password;

    @NotNull
    @Min(value = 0, message = "Negative shift balance not allowed")
    private Double shiftBalance;

    @NotNull
    @Min(value = 0, message = "Negative salary not allowed")
    private Double Salary;


    public static WorkerDto entityToDto(Worker worker){
        return new WorkerDto(
                worker.getId(), worker.getName(), null, worker.getShiftBalance(), worker.getSalary()
        );
    }

    public static Worker dtoToWorker(WorkerDto workerDto){
        return new Worker(
                workerDto.getId(), workerDto.getName(), workerDto.getPassword(),
                workerDto.getShiftBalance(), workerDto.getSalary()
        );
    }
    public static List<Worker> dtoToWorker(List<WorkerDto> workerDtos){
        return workerDtos.stream().map(workerDto -> dtoToWorker(workerDto)).collect(Collectors.toList());
    }
    public static List<WorkerDto> entityToDto(List<Worker> workers){
        return workers.stream().map(worker -> entityToDto(worker)).collect(Collectors.toList());
    }
}
