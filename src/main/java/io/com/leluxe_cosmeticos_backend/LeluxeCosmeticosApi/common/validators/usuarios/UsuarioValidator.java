package io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.common.validators.usuarios;

import java.util.Calendar;
import java.util.Date;

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

    public void validateCreate(UsuarioCreateDTO dto) {
        validateEmailUniqueness(dto.getEmail());
        validateCpfUniqueness(dto.getCpf());
        validateIdadeMinima(dto.getNascimento());
    }

    public void validateCreateWithEndereco(UsuarioComEnderecoCreateDTO dto) {
        validateEmailUniqueness(dto.getEmail());
        validateCpfUniqueness(dto.getCpf());
        validateIdadeMinima(dto.getNascimento());
    }

    public void validateUpdate(UsuarioUpdateDTO dto, Long id) {
        validateExists(id);
        validateEmailUniqueness(dto.getEmail(), id);
        validateCpfUniqueness(dto.getCpf(), id);
        validateIdadeMinima(dto.getNascimento());
    }

    public void validateExists(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RegistroNaoEncontradoException("Usuário", "ID", id.toString());
        }
    }

    public void validateUniqueFields(String email, String cpf) {
        validateEmailUniqueness(email);
        validateCpfUniqueness(cpf);
    }
}
