package io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.services.enderecos;

import java.util.List;
import java.util.Optional;

import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.enderecos.EnderecoCreateDTO;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.enderecos.EnderecoDTO;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.enderecos.EnderecoUpdateDTO;

public interface EnderecoService {

    List<EnderecoDTO> findAll();

    Optional<EnderecoDTO> findById(Long id);

    EnderecoDTO findByIdOrThrow(Long id);

    List<EnderecoDTO> findByUsuarioId(Long usuarioId);

    EnderecoDTO create(EnderecoCreateDTO dto);

    EnderecoDTO updateOrThrow(Long id, EnderecoUpdateDTO dto);

    void delete(Long id);

    EnderecoDTO setPrincipalOrThrow(Long id);
}