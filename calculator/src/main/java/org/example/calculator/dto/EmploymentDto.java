package org.example.calculator.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import org.example.calculator.dto.enums.EmploymentPosition;
import org.example.calculator.dto.enums.EmploymentStatus;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmploymentDto {

    @NotNull(message = "employmentStatus должен быть заполнен")
    private EmploymentStatus employmentStatus;

    @NotBlank(message = "employerINN должен быть заполнен")
    private String employerINN;

    @NotNull(message = "position должен быть заполнен")
    private EmploymentPosition position;

    @NotBlank(message = "workExperienceTotal должен быть заполнен")
    @Min(1)
    private Integer workExperienceTotal;

    @Min(0)
    @NotBlank(message = "workExperienceCurrent должен быть заполнен")
    private Integer workExperienceCurrent;

    @DecimalMin("0.0")
    @NotBlank(message = "salary должен быть заполнен")
    private BigDecimal salary;
}

