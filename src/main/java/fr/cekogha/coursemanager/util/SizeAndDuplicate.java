package fr.cekogha.coursemanager.util;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintTarget;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Size;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {SizeAndDuplicateValidator.class})
public @interface SizeAndDuplicate {
    String message() default "Partant number must be over 3 with no duplicates : ${validatedValue}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    boolean duplicate() default true;
    int minSize() default 0;
    @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
    @Retention(RUNTIME)
    @Documented
    @interface List {

        Size[] value();
    }
}
