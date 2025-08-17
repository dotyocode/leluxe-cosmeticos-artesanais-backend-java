package io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.services.categorias;

import java.util.List;
import java.util.Optional;

import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.categorias.CategoriaCreateDTO;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.categorias.CategoriaDTO;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.categorias.CategoriaUpdateDTO;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.entities.categorias.Categoria;

public interface CategoriaService {

    List<CategoriaDTO> findAll();

    Optional<CategoriaDTO> findById(Long id);

    CategoriaDTO findByIdOrThrow(Long id);

    Categoria findCategoriaEntityByIdOrThrow(Long id);

    Optional<CategoriaDTO> findByNome(String nome);

    CategoriaDTO create(CategoriaCreateDTO dto);

    CategoriaDTO update(Long id, CategoriaUpdateDTO dto);

    void delete(Long id);

    boolean existsById(Long id);

    boolean existsByNome(String nome);
}
