package br.com.oboticariorevenda.oboticario_revenda.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FileValidator.class)
public @interface FileIsImage {
    
    String message() default "O arquivo deve ser uma imagem";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
