package com.fitness.app.componets;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StringValidator implements ConstraintValidator<StringValidate, String> {
    @Override
    public void initialize(StringValidate constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return value!=null &&( value.matches("[a-z]+") || value.matches("[A-Z]+") );
    }
}
