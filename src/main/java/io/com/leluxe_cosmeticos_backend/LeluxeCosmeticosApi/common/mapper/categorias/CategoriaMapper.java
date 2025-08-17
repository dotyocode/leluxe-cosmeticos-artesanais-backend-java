package io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.common.mapper.categorias;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.categorias.CategoriaCreateDTO;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.categorias.CategoriaDTO;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.categorias.CategoriaUpdateDTO;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.entities.categorias.Categoria;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {

    @Mapping(target = "idCategoria", source = "idCategoria")
    CategoriaDTO toDTO(Categoria categoria);

    List<CategoriaDTO> toDTOList(List<Categoria> categorias);

    @Mapping(target = "idCategoria", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Categoria toEntity(CategoriaCreateDTO categoriaCreateDTO);

    @Mapping(target = "idCategoria", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDTO(CategoriaUpdateDTO categoriaUpdateDTO, @MappingTarget Categoria categoria);
}
