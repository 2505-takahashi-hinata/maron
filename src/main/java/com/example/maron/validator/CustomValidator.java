package com.example.maron.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CustomValidator implements ConstraintValidator<Validator, String> {

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
