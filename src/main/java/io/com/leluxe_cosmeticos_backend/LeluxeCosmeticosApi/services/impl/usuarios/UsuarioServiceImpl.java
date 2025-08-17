package io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.services.impl.usuarios;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.common.exceptions.RegistroNaoEncontradoException;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.common.mapper.enderecos.EnderecoMapper;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.common.mapper.usuarios.UsuarioMapper;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.common.validators.usuarios.UsuarioValidator;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.usuarios.UsuarioComEnderecoCreateDTO;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.usuarios.UsuarioCreateDTO;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.usuarios.UsuarioDTO;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.usuarios.UsuarioUpdateDTO;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.entities.enderecos.Endereco;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.entities.usuarios.Usuario;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.repository.enderecos.EnderecoRepository;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.repository.usuarios.UsuarioRepository;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.services.usuarios.UsuarioService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final EnderecoRepository enderecoRepository;
    private final EnderecoMapper enderecoMapper;
    private final UsuarioValidator usuarioValidator;

    @Override
    public List<UsuarioDTO> findAll() {
        return usuarioMapper.toDTOList(usuarioRepository.findAll());
    }

    @Override
    public Optional<UsuarioDTO> findById(Long id) {
        return usuarioRepository.findById(id).map(usuarioMapper::toDTO);
    }

    @Override
    public UsuarioDTO findByIdOrThrow(Long id) {
        return usuarioRepository.findById(id)
                .map(usuarioMapper::toDTO)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Usuário", "ID", id.toString()));
    }

    @Override
    public Usuario findUsuarioEntityByIdOrThrow(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Usuário", "ID", id.toString()));
    }

    @Override
    public Optional<UsuarioDTO> findByEmail(String email) {
        return usuarioRepository.findByEmail(email).map(usuarioMapper::toDTO);
    }

    @Override
    public UsuarioDTO create(UsuarioCreateDTO dto) {
        usuarioValidator.validateCreate(dto);
        Usuario usuario = usuarioMapper.toEntity(dto);
        return usuarioMapper.toDTO(usuarioRepository.save(usuario));
    }

    @Override
    public UsuarioDTO createWithEndereco(UsuarioComEnderecoCreateDTO dto) {
        usuarioValidator.validateCreateWithEndereco(dto);

        Usuario usuario = usuarioMapper.toEntity(dto);
        Usuario savedUsuario = usuarioRepository.save(usuario);

        Endereco endereco = enderecoMapper.toEntity(dto.getEndereco());
        endereco.setUsuario(savedUsuario);
        endereco.setIsPrincipal(true);
        enderecoRepository.save(endereco);

        return usuarioRepository.findByIdWithEnderecos(savedUsuario.getIdUsuario())
                .map(usuarioWithEnderecos -> {
                    return usuarioMapper.toDTO(usuarioWithEnderecos);
                })
                .orElseThrow(() -> new RegistroNaoEncontradoException("Usuário", "ID",
                        savedUsuario.getIdUsuario().toString()));
    }

    @Override
    public UsuarioDTO update(Long id, UsuarioUpdateDTO dto) {
        usuarioValidator.validateUpdate(dto, id);

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Usuário", "ID", id.toString()));

        usuarioMapper.updateEntityFromDTO(dto, usuario);
        return usuarioMapper.toDTO(usuarioRepository.save(usuario));
    }

    @Override
    public void delete(Long id) {
        usuarioValidator.validateExists(id);
        usuarioRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return usuarioRepository.existsById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return usuarioRepository.findByEmail(email).isPresent();
    }
}
