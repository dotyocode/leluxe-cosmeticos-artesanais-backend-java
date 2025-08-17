package io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.categorias;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaUpdateDTO {

    @NotNull(message = "Nome é obrigatório")
    @NotBlank(message = "Nome não pode estar vazio")
    @Size(max = 50, message = "Nome deve ter no máximo 50 caracteres")
    private String nome;
}
