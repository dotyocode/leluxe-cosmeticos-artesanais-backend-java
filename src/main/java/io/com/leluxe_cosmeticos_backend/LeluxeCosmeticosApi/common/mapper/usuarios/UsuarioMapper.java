package io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.common.mapper.usuarios;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.common.mapper.enderecos.EnderecoMapper;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.usuarios.UsuarioComEnderecoCreateDTO;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.usuarios.UsuarioCreateDTO;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.usuarios.UsuarioDTO;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.usuarios.UsuarioUpdateDTO;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.entities.usuarios.Usuario;

@Mapper(componentModel = "spring", uses = { EnderecoMapper.class })
public interface UsuarioMapper {

    @Mapping(target = "idUsuario", source = "idUsuario")
    @Mapping(target = "enderecos", source = "enderecos")
    UsuarioDTO toDTO(Usuario usuario);

    List<UsuarioDTO> toDTOList(List<Usuario> usuarios);

    @Mapping(target = "idUsuario", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "enderecos", ignore = true)
    Usuario toEntity(UsuarioCreateDTO usuarioCreateDTO);

    @Mapping(target = "idUsuario", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "enderecos", ignore = true)
    Usuario toEntity(UsuarioComEnderecoCreateDTO dto);

    @Mapping(target = "idUsuario", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "enderecos", ignore = true)
    void updateEntityFromDTO(UsuarioUpdateDTO usuarioUpdateDTO, @MappingTarget Usuario usuario);
}
