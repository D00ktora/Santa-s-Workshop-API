package Santas_Workshop_API.entity.DTO.gifts.customValidation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = IsWrappedInputCheck.class)
public @interface IsWrappedValidation {
    String message() default "Is Wrapped field must be true of false";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
