package com.fitness.app.componets;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = StringValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface StringValidate {

    String message() default "Invalid String Type";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
