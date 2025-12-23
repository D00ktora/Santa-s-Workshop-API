package Santas_Workshop_API.entity.DTO.elves.customValidation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = ElfLevelCheck.class)
public @interface ElfLevelValidation {
    String message() default "Skill level must be one of the following : JUNIOR, MID, SENIOR";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
