package org.example.deal.entity.pojo;

import lombok.*;
import org.example.deal.dto.enums.EmploymentPosition;
import org.example.deal.dto.enums.EmploymentStatus;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employment {
    private EmploymentStatus status;
    private String employerInn;
    private BigDecimal salary;
    private EmploymentPosition position;
    private Integer workExperienceTotal;
    private Integer workExperienceCurrent;
}