package io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.services.usuarios;

import java.util.List;
import java.util.Optional;

import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.usuarios.UsuarioComEnderecoCreateDTO;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.usuarios.UsuarioCreateDTO;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.usuarios.UsuarioDTO;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.usuarios.UsuarioUpdateDTO;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.entities.usuarios.Usuario;

public interface UsuarioService {

    List<UsuarioDTO> findAll();

    Optional<UsuarioDTO> findById(Long id);

    UsuarioDTO findByIdOrThrow(Long id);

    Usuario findUsuarioEntityByIdOrThrow(Long id);

    Optional<UsuarioDTO> findByEmail(String email);

    UsuarioDTO create(UsuarioCreateDTO dto);

    UsuarioDTO createWithEndereco(UsuarioComEnderecoCreateDTO dto);

    UsuarioDTO update(Long id, UsuarioUpdateDTO dto);

    void delete(Long id);

    boolean existsById(Long id);

    boolean existsByEmail(String email);
}