package io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.common.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.common.validators.annotations.SenhaValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SenhaValidator.class)
public @interface ValidSenha {
    String message() default "Senha deve conter pelo menos uma letra maiúscula, uma minúscula e um número, com mínimo de 8 caracteres";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
