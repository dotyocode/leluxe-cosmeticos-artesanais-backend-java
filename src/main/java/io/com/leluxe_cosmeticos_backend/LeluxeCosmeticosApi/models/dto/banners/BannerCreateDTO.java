package io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.banners;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BannerCreateDTO {

    @NotNull(message = "URL da imagem é obrigatória")
    @NotBlank(message = "URL da imagem não pode estar vazia")
    @Size(max = 500, message = "URL da imagem deve ter no máximo 500 caracteres")
    private String imgUrl;

    @NotNull(message = "Título é obrigatório")
    @NotBlank(message = "Título não pode estar vazio")
    @Size(max = 100, message = "Título deve ter no máximo 100 caracteres")
    private String title;

    @NotNull(message = "Posição é obrigatória")
    private Integer position;

    @NotNull(message = "URL de redirecionamento é obrigatória")
    @NotBlank(message = "URL de redirecionamento não pode estar vazia")
    @Size(max = 500, message = "URL de redirecionamento deve ter no máximo 500 caracteres")
    private String redirectUrl;
}
