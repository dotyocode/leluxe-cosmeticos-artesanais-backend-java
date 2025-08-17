package io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.common.validators.categorias;

import org.springframework.stereotype.Component;

import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.common.exceptions.RegistroDuplicadoException;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.common.exceptions.RegistroNaoEncontradoException;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.categorias.CategoriaCreateDTO;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.categorias.CategoriaUpdateDTO;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.repository.categorias.CategoriaRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CategoriaValidator {

    private final CategoriaRepository categoriaRepository;

    public void validateNomeUniqueness(String nome) {
        if (categoriaRepository.findByNome(nome).isPresent()) {
            throw new RegistroDuplicadoException("Categoria", "nome", nome);
        }
    }

    public void validateNomeUniqueness(String nome, Long excludeId) {
        categoriaRepository.findByNomeExcludingId(nome, excludeId)
                .ifPresent(categoria -> {
                    throw new RegistroDuplicadoException("Categoria", "nome", nome);
                });
    }

    public void validateCreate(CategoriaCreateDTO dto) {
        validateNomeUniqueness(dto.getNome());
    }

    public void validateUpdate(CategoriaUpdateDTO dto, Long id) {
        validateExists(id);
        validateNomeUniqueness(dto.getNome(), id);
    }

    public void validateExists(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new RegistroNaoEncontradoException("Categoria", "ID", id.toString());
        }
    }
}
