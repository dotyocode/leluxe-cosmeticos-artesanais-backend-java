package io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.common.validators.annotations;

import java.util.regex.Pattern;

import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.common.annotations.ValidTelefone;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TelefoneValidator implements ConstraintValidator<ValidTelefone, String> {

    private static final Pattern TELEFONE_PATTERN = Pattern
            .compile("^\\(?[1-9]{2}\\)? ?(?:[2-8]|9[1-9])[0-9]{3}\\-?[0-9]{4}$");

    @Override
    public boolean isValid(String telefone, ConstraintValidatorContext context) {
        if (telefone == null || telefone.trim().isEmpty()) {
            return false;
        }
        return TELEFONE_PATTERN.matcher(telefone.trim()).matches();
    }
}
