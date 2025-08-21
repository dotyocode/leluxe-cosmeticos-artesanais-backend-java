package io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.common.mapper.subcategorias;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.subcategorias.SubCategoriaCreateDTO;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.subcategorias.SubCategoriaDTO;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.subcategorias.SubCategoriaUpdateDTO;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.entities.subcategorias.SubCategoria;

@Mapper(componentModel = "spring")
public interface SubCategoriaMapper {

    SubCategoria toEntity(SubCategoriaCreateDTO createDTO);

    SubCategoriaDTO toDTO(SubCategoria entity);

    void updateEntityFromUpdateDTO(SubCategoriaUpdateDTO updateDTO, @MappingTarget SubCategoria entity);
}
