package org.example.gateway.anno;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.example.gateway.anno.validator.AdultValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AdultValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Adult {
    String message() default "Возраст должен быть не менее 18 лет";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
