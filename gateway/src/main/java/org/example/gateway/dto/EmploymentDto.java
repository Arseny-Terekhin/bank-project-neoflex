package org.example.gateway.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.example.gateway.dto.enums.EmploymentPosition;
import org.example.gateway.dto.enums.EmploymentStatus;

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

    @Min(1)
    @NotBlank(message = "workExperienceCurrent должен быть заполнен")
    private Integer workExperienceCurrent;

    @DecimalMin("1.0")
    @NotBlank(message = "salary должен быть заполнен")
    private BigDecimal salary;
}

