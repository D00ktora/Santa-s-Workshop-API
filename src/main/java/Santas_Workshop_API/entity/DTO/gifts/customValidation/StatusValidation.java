package Santas_Workshop_API.entity.DTO.gifts.customValidation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = GiftStatus.class)
public @interface StatusValidation {
    String message() default "Status must be one of the following: PENDING, READY, LOADED, DELIVERED";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
