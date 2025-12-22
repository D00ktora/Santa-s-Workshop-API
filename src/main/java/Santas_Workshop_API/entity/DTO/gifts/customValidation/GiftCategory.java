package Santas_Workshop_API.entity.DTO.gifts.customValidation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class GiftCategory implements ConstraintValidator<CategoryValidation, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return switch (value) {
            case "TOY", "BOOK", "GADGET", "CLOTHES", "OTHER" -> true;
            default -> false;
        };
    }
}
