package io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.services.impl.categorias;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.common.exceptions.RegistroNaoEncontradoException;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.common.mapper.categorias.CategoriaMapper;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.common.validators.categorias.CategoriaValidator;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.categorias.CategoriaCreateDTO;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.categorias.CategoriaDTO;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.categorias.CategoriaUpdateDTO;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.entities.categorias.Categoria;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.repository.categorias.CategoriaRepository;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.services.categorias.CategoriaService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapper categoriaMapper;
    private final CategoriaValidator categoriaValidator;

    @Override
    public List<CategoriaDTO> findAll() {
        return categoriaMapper.toDTOList(categoriaRepository.findAll());
    }

    @Override
    public Optional<CategoriaDTO> findById(Long id) {
        return categoriaRepository.findById(id).map(categoriaMapper::toDTO);
    }

    @Override
    public CategoriaDTO findByIdOrThrow(Long id) {
        return categoriaRepository.findById(id)
                .map(categoriaMapper::toDTO)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Categoria", "ID", id.toString()));
    }

    @Override
    public Categoria findCategoriaEntityByIdOrThrow(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Categoria", "ID", id.toString()));
    }

    @Override
    public Optional<CategoriaDTO> findByNome(String nome) {
        return categoriaRepository.findByNome(nome).map(categoriaMapper::toDTO);
    }

    @Override
    public CategoriaDTO create(CategoriaCreateDTO dto) {
        categoriaValidator.validateCreate(dto);
        Categoria categoria = categoriaMapper.toEntity(dto);
        return categoriaMapper.toDTO(categoriaRepository.save(categoria));
    }

    @Override
    public CategoriaDTO update(Long id, CategoriaUpdateDTO dto) {
        categoriaValidator.validateUpdate(dto, id);

        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Categoria", "ID", id.toString()));

        categoriaMapper.updateEntityFromDTO(dto, categoria);
        return categoriaMapper.toDTO(categoriaRepository.save(categoria));
    }

    @Override
    public void delete(Long id) {
        categoriaValidator.validateExists(id);
        categoriaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return categoriaRepository.existsById(id);
    }

    @Override
    public boolean existsByNome(String nome) {
        return categoriaRepository.findByNome(nome).isPresent();
    }
}
