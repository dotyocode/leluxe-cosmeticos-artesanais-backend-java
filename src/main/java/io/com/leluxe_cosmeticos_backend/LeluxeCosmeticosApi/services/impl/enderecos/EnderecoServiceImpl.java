package io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.services.impl.enderecos;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.common.exceptions.RegistroNaoEncontradoException;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.common.mapper.enderecos.EnderecoMapper;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.common.validators.enderecos.EnderecoValidator;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.enderecos.EnderecoCreateDTO;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.enderecos.EnderecoDTO;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.enderecos.EnderecoUpdateDTO;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.entities.enderecos.Endereco;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.entities.usuarios.Usuario;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.repository.enderecos.EnderecoRepository;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.services.enderecos.EnderecoService;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.services.usuarios.UsuarioService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class EnderecoServiceImpl implements EnderecoService {

    private final EnderecoRepository enderecoRepository;
    private final UsuarioService usuarioService;
    private final EnderecoMapper enderecoMapper;
    private final EnderecoValidator enderecoValidator;

    @Override
    public List<EnderecoDTO> findAll() {
        return enderecoMapper.toDTOList(enderecoRepository.findAll());
    }

    @Override
    public Optional<EnderecoDTO> findById(Long id) {
        return enderecoRepository.findById(id).map(enderecoMapper::toDTO);
    }

    @Override
    public EnderecoDTO findByIdOrThrow(Long id) {
        return enderecoRepository.findById(id)
                .map(enderecoMapper::toDTO)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Endereço", "ID", id.toString()));
    }

    @Override
    public List<EnderecoDTO> findByUsuarioId(Long usuarioId) {
        return enderecoMapper.toDTOList(enderecoRepository.findByUsuarioIdUsuario(usuarioId));
    }

    @Override
    public EnderecoDTO create(EnderecoCreateDTO dto) {
        enderecoValidator.validateCreate(dto);

        Usuario usuario = usuarioService.findUsuarioEntityByIdOrThrow(dto.getIdUsuario());

        Endereco endereco = enderecoMapper.toEntity(dto);
        endereco.setUsuario(usuario);

        if (Boolean.TRUE.equals(dto.getIsPrincipal())) {
            unsetOtherPrincipals(usuario.getIdUsuario());
        } else if (enderecoRepository.countByUsuarioId(usuario.getIdUsuario()) == 0) {
            endereco.setIsPrincipal(true);
        }

        return enderecoMapper.toDTO(enderecoRepository.save(endereco));
    }

    @Override
    public EnderecoDTO updateOrThrow(Long id, EnderecoUpdateDTO dto) {
        enderecoValidator.validateUpdate(id, dto);

        Endereco endereco = enderecoRepository.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Endereço", "ID", id.toString()));

        if (Boolean.TRUE.equals(dto.getIsPrincipal())) {
            unsetOtherPrincipals(endereco.getUsuario().getIdUsuario());
        }

        enderecoMapper.updateEntityFromDTO(dto, endereco);
        return enderecoMapper.toDTO(enderecoRepository.save(endereco));
    }

    @Override
    public void delete(Long id) {
        enderecoValidator.validateDelete(id);
        enderecoRepository.deleteById(id);
    }

    @Override
    public EnderecoDTO setPrincipalOrThrow(Long id) {
        enderecoValidator.validateSetPrincipal(id);

        Endereco endereco = enderecoRepository.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Endereço", "ID", id.toString()));

        unsetOtherPrincipals(endereco.getUsuario().getIdUsuario());
        endereco.setIsPrincipal(true);
        return enderecoMapper.toDTO(enderecoRepository.save(endereco));
    }

    private void unsetOtherPrincipals(Long usuarioId) {
        List<Endereco> enderecosPrincipais = enderecoRepository.findByUsuarioIdUsuarioAndIsPrincipalTrue(usuarioId);
        for (Endereco endereco : enderecosPrincipais) {
            endereco.setIsPrincipal(false);
            enderecoRepository.save(endereco);
        }
    }
}
