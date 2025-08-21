package io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.services.subcategorias;

import java.util.List;

import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.subcategorias.SubCategoriaCreateDTO;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.subcategorias.SubCategoriaDTO;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.subcategorias.SubCategoriaUpdateDTO;

public interface SubCategoriaService {

    SubCategoriaDTO create(SubCategoriaCreateDTO createDTO);

    SubCategoriaDTO findById(Long id);

    List<SubCategoriaDTO> findAll();

    SubCategoriaDTO update(Long id, SubCategoriaUpdateDTO updateDTO);

    void delete(Long id);
}
