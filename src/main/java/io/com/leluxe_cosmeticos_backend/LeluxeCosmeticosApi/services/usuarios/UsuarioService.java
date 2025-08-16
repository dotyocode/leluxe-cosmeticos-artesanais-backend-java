package io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.services.usuarios;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.common.exceptions.RegistroDuplicadoException;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.common.exceptions.RegistroNaoEncontradoException;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.common.mapper.enderecos.EnderecoMapper;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.common.mapper.usuarios.UsuarioMapper;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.enderecos.EnderecoCreateDTO;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.usuarios.UsuarioComEnderecoCreateDTO;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.usuarios.UsuarioCreateDTO;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.usuarios.UsuarioDTO;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.entities.usuarios.Usuario;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.repository.usuarios.UsuarioRepository;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.services.enderecos.EnderecoService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final EnderecoService enderecoService;
    private final EnderecoMapper enderecoMapper;

    public List<UsuarioDTO> findAll() {
        return usuarioMapper.toDTOList(usuarioRepository.findAll());
    }

    public Optional<UsuarioDTO> findById(Long id) {
        return usuarioRepository.findById(id).map(usuarioMapper::toDTO);
    }

    public Optional<UsuarioDTO> findByEmail(String email) {
        return usuarioRepository.findByEmail(email).map(usuarioMapper::toDTO);
    }

    public UsuarioDTO create(UsuarioCreateDTO dto) {
        validateUniqueFields(dto.getEmail(), dto.getCpf());
        Usuario usuario = usuarioMapper.toEntity(dto);
        return usuarioMapper.toDTO(usuarioRepository.save(usuario));
    }

    public UsuarioDTO createWithEndereco(UsuarioComEnderecoCreateDTO dto) {
        validateUniqueFields(dto.getEmail(), dto.getCpf());

        Usuario usuario = usuarioMapper.toEntity(dto);
        Usuario savedUsuario = usuarioRepository.save(usuario);

        EnderecoCreateDTO enderecoDTO = new EnderecoCreateDTO();
        enderecoDTO.setCep(dto.getEndereco().getCep());
        enderecoDTO.setEndereco(dto.getEndereco().getEndereco());
        enderecoDTO.setNumero(dto.getEndereco().getNumero());
        enderecoDTO.setComplemento(dto.getEndereco().getComplemento());
        enderecoDTO.setBairro(dto.getEndereco().getBairro());
        enderecoDTO.setCidade(dto.getEndereco().getCidade());
        enderecoDTO.setPais(dto.getEndereco().getPais());
        enderecoDTO.setEstado(dto.getEndereco().getEstado());
        enderecoDTO.setIsPrincipal(true);
        enderecoDTO.setIdUsuario(savedUsuario.getIdUsuario());

        enderecoService.create(enderecoDTO);

        return usuarioRepository.findByIdWithEnderecos(savedUsuario.getIdUsuario())
                .map(usuarioWithEnderecos -> {
                    return usuarioMapper.toDTO(usuarioWithEnderecos);
                })
                .orElseThrow(() -> new RegistroNaoEncontradoException("Usuário", "ID",
                        savedUsuario.getIdUsuario().toString()));
    }

    public void delete(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RegistroNaoEncontradoException("Usuário", "ID", id.toString());
        }
        usuarioRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return usuarioRepository.existsById(id);
    }

    public boolean existsByEmail(String email) {
        return usuarioRepository.findByEmail(email).isPresent();
    }

    private void validateUniqueFields(String email, String cpf) {
        if (usuarioRepository.findByEmail(email).isPresent()) {
            throw new RegistroDuplicadoException("Usuário", "email", email);
        }
        if (cpf != null && usuarioRepository.findByCpf(cpf).isPresent()) {
            throw new RegistroDuplicadoException("Usuário", "CPF", cpf);
        }
    }
}