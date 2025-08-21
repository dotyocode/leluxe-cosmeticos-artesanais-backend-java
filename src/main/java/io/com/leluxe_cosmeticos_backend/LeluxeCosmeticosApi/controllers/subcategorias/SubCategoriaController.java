package io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.controllers.subcategorias;

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
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.subcategorias.SubCategoriaCreateDTO;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.subcategorias.SubCategoriaDTO;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.subcategorias.SubCategoriaUpdateDTO;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.services.subcategorias.SubCategoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("subcategorias")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class SubCategoriaController {

        private final SubCategoriaService subCategoriaService;

        @PostMapping
        public ResponseEntity<SuccessResponse<SubCategoriaDTO>> create(
                        @Valid @RequestBody SubCategoriaCreateDTO createDTO) {
                SubCategoriaDTO createdSubCategoria = subCategoriaService.create(createDTO);

                String path = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest()
                                .getRequestURI();

                SuccessResponse<SubCategoriaDTO> response = SuccessResponse.of(
                                createdSubCategoria,
                                "Subcategoria criada com sucesso",
                                HttpStatus.CREATED.value(),
                                path);

                return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        @GetMapping("/{id}")
        public ResponseEntity<SuccessResponse<SubCategoriaDTO>> findById(@PathVariable Long id) {
                SubCategoriaDTO subCategoria = subCategoriaService.findById(id);

                String path = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest()
                                .getRequestURI();

                SuccessResponse<SubCategoriaDTO> response = SuccessResponse.of(
                                subCategoria,
                                "Subcategoria encontrada com sucesso",
                                HttpStatus.OK.value(),
                                path);

                return ResponseEntity.ok(response);
        }

        @GetMapping
        public ResponseEntity<SuccessResponse<List<SubCategoriaDTO>>> findAll() {
                List<SubCategoriaDTO> subCategorias = subCategoriaService.findAll();

                String path = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest()
                                .getRequestURI();

                SuccessResponse<List<SubCategoriaDTO>> response = SuccessResponse.of(
                                subCategorias,
                                "Subcategorias encontradas com sucesso",
                                HttpStatus.OK.value(),
                                path);

                return ResponseEntity.ok(response);
        }

        @PutMapping("/{id}")
        public ResponseEntity<SuccessResponse<SubCategoriaDTO>> update(
                        @PathVariable Long id,
                        @Valid @RequestBody SubCategoriaUpdateDTO updateDTO) {
                SubCategoriaDTO updatedSubCategoria = subCategoriaService.update(id, updateDTO);

                String path = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest()
                                .getRequestURI();

                SuccessResponse<SubCategoriaDTO> response = SuccessResponse.of(
                                updatedSubCategoria,
                                "Subcategoria atualizada com sucesso",
                                HttpStatus.OK.value(),
                                path);

                return ResponseEntity.ok(response);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<SuccessResponse<Void>> delete(@PathVariable Long id) {
                subCategoriaService.delete(id);

                String path = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest()
                                .getRequestURI();

                SuccessResponse<Void> response = SuccessResponse.of(
                                null,
                                "Subcategoria excluída com sucesso",
                                HttpStatus.OK.value(),
                                path);

                return ResponseEntity.ok(response);
        }
}
