package io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.enderecos;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoCreateWithoutUsuarioDTO {

    @Size(max = 50, message = "CEP deve ter no máximo 50 caracteres")
    private String cep;

    @Size(max = 250, message = "Endereço deve ter no máximo 250 caracteres")
    private String endereco;

    private Integer numero;

    @Size(max = 50, message = "Complemento deve ter no máximo 50 caracteres")
    private String complemento;

    @Size(max = 50, message = "Bairro deve ter no máximo 50 caracteres")
    private String bairro;

    @Size(max = 50, message = "Cidade deve ter no máximo 50 caracteres")
    private String cidade;

    @Size(max = 50, message = "País deve ter no máximo 50 caracteres")
    private String pais;

    @Size(max = 50, message = "Estado deve ter no máximo 50 caracteres")
    private String estado;

    private Boolean isPrincipal = false;
}
