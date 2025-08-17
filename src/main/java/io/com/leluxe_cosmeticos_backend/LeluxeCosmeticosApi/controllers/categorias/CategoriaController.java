package io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.controllers.categorias;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.common.exceptions.GlobalExceptionHandler.SuccessResponse;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.categorias.CategoriaCreateDTO;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.categorias.CategoriaDTO;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.categorias.CategoriaUpdateDTO;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.services.categorias.CategoriaService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("categorias")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CategoriaController {

    private final CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> findAll() {
        List<CategoriaDTO> categorias = categoriaService.findAll();
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> findById(@PathVariable Long id) {
        CategoriaDTO categoria = categoriaService.findByIdOrThrow(id);
        return ResponseEntity.ok(categoria);
    }

    @PostMapping
    public ResponseEntity<SuccessResponse<CategoriaDTO>> create(
            @Valid @RequestBody CategoriaCreateDTO categoriaCreateDTO) {
        CategoriaDTO createdCategoria = categoriaService.create(categoriaCreateDTO);

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
        String path = request.getRequestURI();

        SuccessResponse<CategoriaDTO> response = SuccessResponse.of(
                createdCategoria,
                "Categoria criada com sucesso",
                HttpStatus.CREATED.value(),
                path);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponse<CategoriaDTO>> update(@PathVariable Long id,
            @Valid @RequestBody CategoriaUpdateDTO dto) {
        CategoriaDTO updatedCategoria = categoriaService.update(id, dto);

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
        String path = request.getRequestURI();

        SuccessResponse<CategoriaDTO> response = SuccessResponse.of(
                updatedCategoria,
                "Categoria atualizada com sucesso",
                HttpStatus.OK.value(),
                path);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse<Void>> delete(@PathVariable Long id) {
        categoriaService.delete(id);

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
        String path = request.getRequestURI();

        SuccessResponse<Void> response = SuccessResponse.of(
                null,
                "Categoria excluída com sucesso",
                HttpStatus.OK.value(),
                path);

        return ResponseEntity.ok(response);
    }
}
