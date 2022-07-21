package com.fitness.app.utils;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class InputStringValidator implements ConstraintValidator<InputStringValidate, String> {

    @Override
    public void initialize(InputStringValidate constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value!=null && value.matches("[a-z]+") && value.matches("[A-Z]+") && !value.matches("[]");
    }
}
