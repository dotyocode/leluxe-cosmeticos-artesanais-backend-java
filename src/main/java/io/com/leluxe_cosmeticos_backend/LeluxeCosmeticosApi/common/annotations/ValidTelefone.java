package io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.common.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.common.validators.annotations.TelefoneValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TelefoneValidator.class)
public @interface ValidTelefone {
    String message() default "Telefone deve estar no formato brasileiro válido (ex: (11) 99999-9999)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
