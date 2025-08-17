package io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.common.mapper.enderecos;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.enderecos.EnderecoCreateDTO;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.enderecos.EnderecoCreateWithoutUsuarioDTO;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.enderecos.EnderecoDTO;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.enderecos.EnderecoUpdateDTO;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.entities.enderecos.Endereco;

@Mapper(componentModel = "spring")
public interface EnderecoMapper {

    @Mapping(target = "idEndereco", source = "idEndereco")
    @Mapping(target = "idUsuario", source = "usuario.idUsuario")
    EnderecoDTO toDTO(Endereco endereco);

    List<EnderecoDTO> toDTOList(List<Endereco> enderecos);

    @Mapping(target = "idEndereco", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    Endereco toEntity(EnderecoCreateDTO enderecoCreateDTO);

    @Mapping(target = "idEndereco", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    Endereco toEntity(EnderecoCreateWithoutUsuarioDTO enderecoCreateWithoutUsuarioDTO);

    @Mapping(target = "idEndereco", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    void updateEntityFromDTO(EnderecoUpdateDTO enderecoUpdateDTO, @MappingTarget Endereco endereco);
}