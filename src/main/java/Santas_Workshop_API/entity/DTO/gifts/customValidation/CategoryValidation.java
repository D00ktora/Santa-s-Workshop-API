package Santas_Workshop_API.entity.DTO.gifts.customValidation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = GiftCategory.class)
public @interface CategoryValidation {
    String message() default "Category must be one of the following: TOY, BOOK ,GADGET ,CLOTHES ,OTHER";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
