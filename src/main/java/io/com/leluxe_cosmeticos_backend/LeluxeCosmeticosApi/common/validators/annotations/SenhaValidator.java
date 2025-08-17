package io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.common.validators.annotations;

import java.util.regex.Pattern;

import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.common.annotations.ValidSenha;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SenhaValidator implements ConstraintValidator<ValidSenha, String> {

    private static final Pattern SENHA_PATTERN = Pattern
            .compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d@$!%*?&]{8,}$");

    @Override
    public boolean isValid(String senha, ConstraintValidatorContext context) {
        if (senha == null || senha.trim().isEmpty()) {
            return false;
        }
        return SENHA_PATTERN.matcher(senha).matches();
    }
}
