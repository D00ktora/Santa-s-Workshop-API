package Santas_Workshop_API.entity.DTO.elves.customValidation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ElfLevelCheck implements ConstraintValidator<ElfLevelValidation, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return switch (value) {
            case "JUNIOR", "MID", "SENIOR" -> true;
            default -> false;
        };
    }
}
