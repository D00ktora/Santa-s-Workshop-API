package Santas_Workshop_API.entity.DTO.gifts.customValidation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class GiftStatus implements ConstraintValidator<StatusValidation, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return switch (value) {
            case "PENDING", "READY", "LOADED", "DELIVERED" -> true;
            default -> false;
        };
    }
}
