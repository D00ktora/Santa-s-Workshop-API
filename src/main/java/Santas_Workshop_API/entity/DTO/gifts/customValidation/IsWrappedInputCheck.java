package Santas_Workshop_API.entity.DTO.gifts.customValidation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IsWrappedInputCheck implements ConstraintValidator<IsWrappedValidation, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return false;
        if (value.equals("true") || value.equals("false")) {
            return true;
        } else return false;
    }
}
