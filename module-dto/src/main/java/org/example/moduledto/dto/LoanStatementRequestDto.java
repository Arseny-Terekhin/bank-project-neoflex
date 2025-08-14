package org.example.moduledto.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import org.example.moduledto.anno.Adult;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanStatementRequestDto {

    @NotNull(message = "amount должен быть заполнен")
    @DecimalMin(value = "20000", message = "Сумма кредита должна быть не менее 20 000")
    private BigDecimal amount;

    @NotNull(message = "term должен быть заполнен")
    @Min(value = 6, message = "Срок кредита должен быть не менее 6 месяцев")
    private Integer term;

    @NotBlank(message = "firstName должен быть заполнен")
    private String firstName;

    @NotBlank(message = "lastName должен быть заполнен")
    private String lastName;

    @NotBlank(message = "middleName должен быть заполнен")
    private String middleName;

    @NotBlank(message = "email должен быть заполнен")
    @Pattern(regexp = "^[a-z0-9A-Z_!#$%&'*+/=?`{|}~^.-]+@[a-z0-9A-Z.-]+$", message = "email введен не коректно")
    private String email;

    @Adult
    @NotNull(message = "birthdate должен быть заполнен")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthdate;

    @NotBlank(message = "passportSeries должен быть заполнен")
    @Size(min = 4, max = 4, message = "Серия паспорта должна состоять из 4 цифр")
    private String passportSeries;

    @NotBlank(message = "passportNumber должен быть заполнен")
    @Size(min = 6, max = 6, message = "Номер паспорта должна состоять из 6 цифр")
    private String passportNumber;

}
