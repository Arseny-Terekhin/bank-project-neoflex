package org.example.moduledto.anno.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.moduledto.anno.Adult;

import java.time.LocalDate;

public class AdultValidator implements ConstraintValidator<Adult, LocalDate> {

    @Override
    public boolean isValid(LocalDate dateOfBirth, ConstraintValidatorContext context) {
        LocalDate today = LocalDate.now();
        return !dateOfBirth.isAfter(today.minusYears(18));
    }
}
