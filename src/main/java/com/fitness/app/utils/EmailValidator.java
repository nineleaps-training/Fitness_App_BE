package com.fitness.app.utils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<EmailValidate, String> {
    @Override
    public void initialize(EmailValidate constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value!=null && value.matches("[a-z]+") && (value.contains("@"));
    }
}
