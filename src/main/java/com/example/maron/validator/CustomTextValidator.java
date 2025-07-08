package com.example.maron.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CustomTextValidator implements ConstraintValidator<TextValidator, String> {

    @Override
    public boolean isValid(String input, ConstraintValidatorContext con) {
        if (input == null) {
            return false;
        }
        if (input.strip().isEmpty()) {
            return false;
        }
        return true;
    }
}
