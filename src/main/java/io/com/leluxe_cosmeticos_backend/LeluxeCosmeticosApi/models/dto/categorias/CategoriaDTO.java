package io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.categorias;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaDTO {

    private Long idCategoria;
    private String nome;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
