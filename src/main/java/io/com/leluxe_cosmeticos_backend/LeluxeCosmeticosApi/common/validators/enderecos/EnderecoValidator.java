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
        if (cep == null || cep.trim().isEmpty()) {
            throw new CampoInvalidoException("cep", cep, "CEP é obrigatório");
        }

        String cepLimpo = cep.trim().replace("-", "");
        if (!CEP_PATTERN.matcher(cepLimpo).matches()) {
            throw new CampoInvalidoException("cep", cep,
                    "CEP deve estar no formato válido (ex: 12345-678 ou 12345678)");
        }
    }

    public void validateNumero(Integer numero) {
        if (numero == null) {
            throw new CampoInvalidoException("numero", "null", "Número é obrigatório");
        }

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

        if (dto.getEndereco() == null || dto.getEndereco().trim().isEmpty()) {
            throw new CampoInvalidoException("endereco", dto.getEndereco(), "Endereço é obrigatório");
        }

        if (dto.getBairro() == null || dto.getBairro().trim().isEmpty()) {
            throw new CampoInvalidoException("bairro", dto.getBairro(), "Bairro é obrigatório");
        }

        if (dto.getCidade() == null || dto.getCidade().trim().isEmpty()) {
            throw new CampoInvalidoException("cidade", dto.getCidade(), "Cidade é obrigatória");
        }

        if (dto.getEstado() == null || dto.getEstado().trim().isEmpty()) {
            throw new CampoInvalidoException("estado", dto.getEstado(), "Estado é obrigatório");
        }

        if (dto.getPais() == null || dto.getPais().trim().isEmpty()) {
            throw new CampoInvalidoException("pais", dto.getPais(), "País é obrigatório");
        }
    }

    public void validateUpdate(Long id, EnderecoUpdateDTO dto) {
        validateExists(id);
        validateFormatoCep(dto.getCep());
        validateNumero(dto.getNumero());

        if (dto.getEndereco() == null || dto.getEndereco().trim().isEmpty()) {
            throw new CampoInvalidoException("endereco", dto.getEndereco(), "Endereço é obrigatório");
        }

        if (dto.getBairro() == null || dto.getBairro().trim().isEmpty()) {
            throw new CampoInvalidoException("bairro", dto.getBairro(), "Bairro é obrigatório");
        }

        if (dto.getCidade() == null || dto.getCidade().trim().isEmpty()) {
            throw new CampoInvalidoException("cidade", dto.getCidade(), "Cidade é obrigatória");
        }

        if (dto.getEstado() == null || dto.getEstado().trim().isEmpty()) {
            throw new CampoInvalidoException("estado", dto.getEstado(), "Estado é obrigatório");
        }

        if (dto.getPais() == null || dto.getPais().trim().isEmpty()) {
            throw new CampoInvalidoException("pais", dto.getPais(), "País é obrigatório");
        }
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
