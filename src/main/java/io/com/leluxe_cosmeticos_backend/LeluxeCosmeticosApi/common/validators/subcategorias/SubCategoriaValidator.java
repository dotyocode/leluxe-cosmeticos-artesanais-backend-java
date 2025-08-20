package io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.common.validators.subcategorias;

import org.springframework.stereotype.Component;

import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.common.exceptions.RegistroDuplicadoException;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.common.exceptions.RegistroNaoEncontradoException;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.subcategorias.SubCategoriaCreateDTO;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.subcategorias.SubCategoriaUpdateDTO;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.repository.subcategorias.SubCategoriaRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SubCategoriaValidator {

    private final SubCategoriaRepository subCategoriaRepository;

    public void validateCreate(SubCategoriaCreateDTO createDTO) {
        validateNome(createDTO.getNome());
        validateNomeUnico(createDTO.getNome());
    }

    public void validateUpdate(Long id, SubCategoriaUpdateDTO updateDTO) {
        validateNome(updateDTO.getNome());
        validateNomeUnicoExcludingId(updateDTO.getNome(), id);
    }

    public void validateExists(Long id) {
        if (!subCategoriaRepository.existsById(id)) {
            throw new RegistroNaoEncontradoException("Subcategoria", "ID", id.toString());
        }
    }

    private void validateNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome da subcategoria é obrigatório");
        }
        if (nome.length() > 250) {
            throw new IllegalArgumentException("Nome da subcategoria deve ter no máximo 250 caracteres");
        }
    }

    private void validateNomeUnico(String nome) {
        if (subCategoriaRepository.existsByNome(nome)) {
            throw new RegistroDuplicadoException("Subcategoria", "nome", nome);
        }
    }

    private void validateNomeUnicoExcludingId(String nome, Long excludeId) {
        if (subCategoriaRepository.findByNomeExcludingId(nome, excludeId).isPresent()) {
            throw new RegistroDuplicadoException("Subcategoria", "nome", nome);
        }
    }
}
