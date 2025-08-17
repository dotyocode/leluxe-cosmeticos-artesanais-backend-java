package io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.enderecos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoUpdateDTO {

    @NotNull(message = "CEP é obrigatório")
    @NotBlank(message = "CEP não pode estar vazio")
    @Size(max = 50, message = "CEP deve ter no máximo 50 caracteres")
    private String cep;

    @NotNull(message = "Endereço é obrigatório")
    @NotBlank(message = "Endereço não pode estar vazio")
    @Size(max = 250, message = "Endereço deve ter no máximo 250 caracteres")
    private String endereco;

    @NotNull(message = "Número é obrigatório")
    private Integer numero;

    @NotNull(message = "Complemento é obrigatório")
    @NotBlank(message = "Complemento não pode estar vazio")
    @Size(max = 50, message = "Complemento deve ter no máximo 50 caracteres")
    private String complemento;

    @NotNull(message = "Bairro é obrigatório")
    @NotBlank(message = "Bairro não pode estar vazio")
    @Size(max = 50, message = "Bairro deve ter no máximo 50 caracteres")
    private String bairro;

    @NotNull(message = "Cidade é obrigatória")
    @NotBlank(message = "Cidade não pode estar vazia")
    @Size(max = 50, message = "Cidade deve ter no máximo 50 caracteres")
    private String cidade;

    @NotNull(message = "País é obrigatório")
    @NotBlank(message = "País não pode estar vazio")
    @Size(max = 50, message = "País deve ter no máximo 50 caracteres")
    private String pais;

    @NotNull(message = "Estado é obrigatório")
    @NotBlank(message = "Estado não pode estar vazio")
    @Size(max = 50, message = "Estado deve ter no máximo 50 caracteres")
    private String estado;

    private Boolean isPrincipal;
}