package io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.common.validators.usuarios;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.common.exceptions.CampoInvalidoException;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.common.exceptions.RegistroDuplicadoException;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.common.exceptions.RegistroNaoEncontradoException;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.usuarios.UsuarioComEnderecoCreateDTO;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.usuarios.UsuarioCreateDTO;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.usuarios.UsuarioUpdateDTO;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.repository.usuarios.UsuarioRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UsuarioValidator {

    private final UsuarioRepository usuarioRepository;

    private static final int IDADE_MINIMA = 18;
    private static final Pattern TELEFONE_PATTERN = Pattern
            .compile("^\\(?[1-9]{2}\\)? ?(?:[2-8]|9[1-9])[0-9]{3}\\-?[0-9]{4}$");
    private static final Pattern SENHA_PATTERN = Pattern
            .compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d@$!%*?&]{8,}$");

    public void validateEmailUniqueness(String email) {
        if (usuarioRepository.findByEmail(email).isPresent()) {
            throw new RegistroDuplicadoException("Usuário", "email", email);
        }
    }

    public void validateEmailUniqueness(String email, Long excludeId) {
        usuarioRepository.findByEmail(email)
                .filter(usuario -> !usuario.getIdUsuario().equals(excludeId))
                .ifPresent(usuario -> {
                    throw new RegistroDuplicadoException("Usuário", "email", email);
                });
    }

    public void validateCpfUniqueness(String cpf) {
        if (cpf != null && usuarioRepository.findByCpf(cpf).isPresent()) {
            throw new RegistroDuplicadoException("Usuário", "CPF", cpf);
        }
    }

    public void validateCpfUniqueness(String cpf, Long excludeId) {
        if (cpf != null) {
            usuarioRepository.findByCpf(cpf)
                    .filter(usuario -> !usuario.getIdUsuario().equals(excludeId))
                    .ifPresent(usuario -> {
                        throw new RegistroDuplicadoException("Usuário", "CPF", cpf);
                    });
        }
    }

    public void validateIdadeMinima(Date nascimento) {
        if (nascimento == null) {
            throw new CampoInvalidoException("nascimento", "null", "Data de nascimento é obrigatória");
        }

        Calendar dataNascimento = Calendar.getInstance();
        dataNascimento.setTime(nascimento);

        Calendar dataAtual = Calendar.getInstance();
        Calendar dataMinima = Calendar.getInstance();
        dataMinima.add(Calendar.YEAR, -IDADE_MINIMA);

        if (dataNascimento.after(dataMinima)) {
            throw new CampoInvalidoException("nascimento", nascimento.toString(),
                    String.format("Usuário deve ter pelo menos %d anos", IDADE_MINIMA));
        }
    }

    public void validateFormatoTelefone(String telefone) {
        if (telefone == null || telefone.trim().isEmpty()) {
            throw new CampoInvalidoException("telefone", telefone, "Telefone é obrigatório");
        }

        if (!TELEFONE_PATTERN.matcher(telefone.trim()).matches()) {
            throw new CampoInvalidoException("telefone", telefone,
                    "Telefone deve estar no formato brasileiro válido (ex: (11) 99999-9999)");
        }
    }

    public void validateForcaSenha(String senha) {
        if (senha == null || senha.trim().isEmpty()) {
            throw new CampoInvalidoException("password", senha, "Senha é obrigatória");
        }

        if (senha.length() < 8) {
            throw new CampoInvalidoException("password", "***", "Senha deve ter pelo menos 8 caracteres");
        }

        if (!SENHA_PATTERN.matcher(senha).matches()) {
            throw new CampoInvalidoException("password", "***",
                    "Senha deve conter pelo menos uma letra maiúscula, uma minúscula e um número");
        }
    }

    public void validateExists(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RegistroNaoEncontradoException("Usuário", "ID", id.toString());
        }
    }

    public void validateCreate(UsuarioCreateDTO dto) {
        validateEmailUniqueness(dto.getEmail());
        validateCpfUniqueness(dto.getCpf());
        validateIdadeMinima(dto.getNascimento());
        validateFormatoTelefone(dto.getTelefone());
        validateForcaSenha(dto.getPassword());
    }

    public void validateCreateWithEndereco(UsuarioComEnderecoCreateDTO dto) {
        validateEmailUniqueness(dto.getEmail());
        validateCpfUniqueness(dto.getCpf());
        validateIdadeMinima(dto.getNascimento());
        validateFormatoTelefone(dto.getTelefone());
        validateForcaSenha(dto.getPassword());

        if (dto.getEndereco() == null) {
            throw new CampoInvalidoException("endereco", "null", "Endereço é obrigatório");
        }
    }

    public void validateUpdate(UsuarioUpdateDTO dto, Long id) {
        validateExists(id);
        validateEmailUniqueness(dto.getEmail(), id);
        validateCpfUniqueness(dto.getCpf(), id);
        validateIdadeMinima(dto.getNascimento());
        validateFormatoTelefone(dto.getTelefone());
        validateForcaSenha(dto.getPassword());
    }

    public void validateUniqueFields(String email, String cpf) {
        validateEmailUniqueness(email);
        validateCpfUniqueness(cpf);
    }
}
