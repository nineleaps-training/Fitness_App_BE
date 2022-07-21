package com.fitness.app.utils;

import com.nimbusds.jose.Payload;

import javax.validation.Constraint;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EmailValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailValidate {

    String message() default "Invalid email type";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
