package io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.common.validators.enderecos;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.common.exceptions.CampoInvalidoException;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.common.exceptions.RegistroNaoEncontradoException;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.enderecos.EnderecoCreateDTO;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.enderecos.EnderecoUpdateDTO;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.entities.enderecos.Endereco;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.repository.enderecos.EnderecoRepository;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.repository.usuarios.UsuarioRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EnderecoValidator {

    private final EnderecoRepository enderecoRepository;
    private final UsuarioRepository usuarioRepository;

    private static final Pattern CEP_PATTERN = Pattern.compile("^\\d{5}-?\\d{3}$");
    private static final int NUMERO_MAXIMO = 99999;
    private static final int NUMERO_MINIMO = 1;

    public void validateExists(Long id) {
        if (!enderecoRepository.existsById(id)) {
            throw new RegistroNaoEncontradoException("Endereço", "ID", id.toString());
        }
    }

    public void validateUsuarioExists(Long usuarioId) {
        if (!usuarioRepository.existsById(usuarioId)) {
            throw new RegistroNaoEncontradoException("Usuário", "ID", usuarioId.toString());
        }
    }

    public void validateFormatoCep(String cep) {
        String cepLimpo = cep.trim().replace("-", "");
        if (!CEP_PATTERN.matcher(cepLimpo).matches()) {
            throw new CampoInvalidoException("cep", cep,
                    "CEP deve estar no formato válido (ex: 12345-678 ou 12345678)");
        }
    }

    public void validateNumero(Integer numero) {
        if (numero < NUMERO_MINIMO || numero > NUMERO_MAXIMO) {
            throw new CampoInvalidoException("numero", numero.toString(),
                    String.format("Número deve estar entre %d e %d", NUMERO_MINIMO, NUMERO_MAXIMO));
        }
    }

    public void validateEnderecoPertenceAoUsuario(Long enderecoId, Long usuarioId) {
        Endereco endereco = enderecoRepository.findById(enderecoId)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Endereço", "ID", enderecoId.toString()));

        if (!endereco.getUsuario().getIdUsuario().equals(usuarioId)) {
            throw new CampoInvalidoException("endereco", enderecoId.toString(),
                    "Endereço não pertence ao usuário especificado");
        }
    }

    public void validateEnderecoPrincipal(Long usuarioId, Boolean isPrincipal) {
        if (Boolean.TRUE.equals(isPrincipal)) {
            List<Endereco> enderecosPrincipais = enderecoRepository
                    .findByUsuarioIdUsuarioAndIsPrincipalTrue(usuarioId);

            if (!enderecosPrincipais.isEmpty()) {
                throw new CampoInvalidoException("isPrincipal", "true",
                        "Usuário já possui um endereço principal. Defina outro endereço como principal primeiro.");
            }
        }
    }

    public void validatePodeRemoverEndereco(Long enderecoId) {
        Endereco endereco = enderecoRepository.findById(enderecoId)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Endereço", "ID", enderecoId.toString()));

        Long usuarioId = endereco.getUsuario().getIdUsuario();
        long totalEnderecos = enderecoRepository.countByUsuarioId(usuarioId);

        if (totalEnderecos <= 1) {
            throw new CampoInvalidoException("endereco", enderecoId.toString(),
                    "Não é possível remover o único endereço do usuário");
        }
    }

    public void validateCreate(EnderecoCreateDTO dto) {
        validateUsuarioExists(dto.getIdUsuario());
        validateFormatoCep(dto.getCep());
        validateNumero(dto.getNumero());
    }

    public void validateUpdate(Long id, EnderecoUpdateDTO dto) {
        validateExists(id);
        validateFormatoCep(dto.getCep());
        validateNumero(dto.getNumero());
    }

    public void validateSetPrincipal(Long id) {
        validateExists(id);

        Endereco endereco = enderecoRepository.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Endereço", "ID", id.toString()));

        if (Boolean.TRUE.equals(endereco.getIsPrincipal())) {
            throw new CampoInvalidoException("isPrincipal", "true",
                    "Este endereço já é o principal");
        }
    }

    public void validateDelete(Long id) {
        validateExists(id);
        validatePodeRemoverEndereco(id);
    }
}
