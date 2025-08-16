package io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.services.enderecos;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.common.mapper.enderecos.EnderecoMapper;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.common.validators.enderecos.EnderecoValidator;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.enderecos.EnderecoCreateDTO;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.enderecos.EnderecoDTO;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.enderecos.EnderecoUpdateDTO;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.entities.enderecos.Endereco;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.entities.usuarios.Usuario;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.repository.enderecos.EnderecoRepository;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.repository.usuarios.UsuarioRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;
    private final UsuarioRepository usuarioRepository;
    private final EnderecoMapper enderecoMapper;
    private final EnderecoValidator enderecoValidator;

    public List<EnderecoDTO> findAll() {
        return enderecoMapper.toDTOList(enderecoRepository.findAll());
    }

    public Optional<EnderecoDTO> findById(Long id) {
        return enderecoRepository.findById(id).map(enderecoMapper::toDTO);
    }

    public List<EnderecoDTO> findByUsuarioId(Long usuarioId) {
        return enderecoMapper.toDTOList(enderecoRepository.findByUsuarioIdUsuario(usuarioId));
    }

    public EnderecoDTO create(EnderecoCreateDTO dto) {
        enderecoValidator.validateCreate(dto);

        Usuario usuario = findUsuarioOrThrow(dto.getIdUsuario());
        Endereco endereco = enderecoMapper.toEntity(dto);
        endereco.setUsuario(usuario);

        if (Boolean.TRUE.equals(dto.getIsPrincipal())) {
            unsetOtherPrincipals(usuario.getIdUsuario());
        } else if (enderecoRepository.countByUsuarioId(usuario.getIdUsuario()) == 0) {
            endereco.setIsPrincipal(true);
        }

        return enderecoMapper.toDTO(enderecoRepository.save(endereco));
    }

    public Optional<EnderecoDTO> update(Long id, EnderecoUpdateDTO dto) {
        enderecoValidator.validateUpdate(id, dto);

        return enderecoRepository.findById(id)
                .map(endereco -> {
                    if (Boolean.TRUE.equals(dto.getIsPrincipal())) {
                        unsetOtherPrincipals(endereco.getUsuario().getIdUsuario());
                    }
                    enderecoMapper.updateEntityFromDTO(dto, endereco);
                    return enderecoMapper.toDTO(enderecoRepository.save(endereco));
                });
    }

    public void delete(Long id) {
        enderecoValidator.validateDelete(id);
        enderecoRepository.deleteById(id);
    }

    public Optional<EnderecoDTO> setPrincipal(Long id) {
        enderecoValidator.validateSetPrincipal(id);

        return enderecoRepository.findById(id)
                .map(endereco -> {
                    unsetOtherPrincipals(endereco.getUsuario().getIdUsuario());
                    endereco.setIsPrincipal(true);
                    return enderecoMapper.toDTO(enderecoRepository.save(endereco));
                });
    }

    private Usuario findUsuarioOrThrow(Long usuarioId) {
        return usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    private void unsetOtherPrincipals(Long usuarioId) {
        List<Endereco> enderecosPrincipais = enderecoRepository.findByUsuarioIdUsuarioAndIsPrincipalTrue(usuarioId);
        for (Endereco endereco : enderecosPrincipais) {
            endereco.setIsPrincipal(false);
            enderecoRepository.save(endereco);
        }
    }
}