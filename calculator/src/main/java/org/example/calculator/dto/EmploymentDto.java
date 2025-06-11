package org.example.calculator.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class EmploymentDto {

    @NotNull
    private EmploymentStatus employmentStatus;

    @NotBlank
    private String employerINN;

    @NotBlank
    private String position;

    @NotBlank
    @Min(1)
    private Integer workExperienceTotal;
    @Min(0)
    @NotBlank
    private Integer workExperienceCurrent;

    @DecimalMin("0.0")
    @NotBlank
    private BigDecimal salary;
}

enum EmploymentStatus{
    EMPLOYED,
    UNEMPLOYED
}