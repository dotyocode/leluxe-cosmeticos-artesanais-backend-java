package io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.services.impl.subcategorias;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.common.exceptions.RegistroNaoEncontradoException;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.common.mapper.subcategorias.SubCategoriaMapper;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.common.validators.subcategorias.SubCategoriaValidator;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.subcategorias.SubCategoriaCreateDTO;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.subcategorias.SubCategoriaDTO;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.subcategorias.SubCategoriaUpdateDTO;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.entities.subcategorias.SubCategoria;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.repository.subcategorias.SubCategoriaRepository;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.services.subcategorias.SubCategoriaService;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class SubCategoriaServiceImpl implements SubCategoriaService {

    private final SubCategoriaRepository subCategoriaRepository;
    private final SubCategoriaMapper subCategoriaMapper;
    private final SubCategoriaValidator subCategoriaValidator;

    @Override
    public SubCategoriaDTO create(SubCategoriaCreateDTO createDTO) {
        subCategoriaValidator.validateCreate(createDTO);

        SubCategoria subCategoria = subCategoriaMapper.toEntity(createDTO);
        SubCategoria savedSubCategoria = subCategoriaRepository.save(subCategoria);

        return subCategoriaMapper.toDTO(savedSubCategoria);
    }

    @Override
    public SubCategoriaDTO findById(Long id) {
        SubCategoria subCategoria = findEntityByIdOrThrow(id);
        return subCategoriaMapper.toDTO(subCategoria);
    }

    @Override
    public List<SubCategoriaDTO> findAll() {
        List<SubCategoria> subCategorias = subCategoriaRepository.findAll();
        return subCategorias.stream()
                .map(subCategoriaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SubCategoriaDTO update(Long id, SubCategoriaUpdateDTO updateDTO) {
        subCategoriaValidator.validateUpdate(id, updateDTO);

        SubCategoria subCategoria = findEntityByIdOrThrow(id);
        subCategoriaMapper.updateEntityFromUpdateDTO(updateDTO, subCategoria);

        SubCategoria updatedSubCategoria = subCategoriaRepository.save(subCategoria);
        return subCategoriaMapper.toDTO(updatedSubCategoria);
    }

    @Override
    public void delete(Long id) {
        subCategoriaValidator.validateExists(id);
        subCategoriaRepository.deleteById(id);
    }

    private SubCategoria findEntityByIdOrThrow(Long id) {
        return subCategoriaRepository.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Subcategoria", "ID", id.toString()));
    }
}
