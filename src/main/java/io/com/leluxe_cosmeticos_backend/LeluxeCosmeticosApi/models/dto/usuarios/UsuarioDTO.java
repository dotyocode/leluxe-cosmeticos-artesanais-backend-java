package io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.usuarios;

import java.time.LocalDateTime;
import java.util.List;

import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.enderecos.EnderecoDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {

    private Long idUsuario;

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 250, message = "Nome deve ter no máximo 250 caracteres")
    private String nome;

    @Size(max = 50, message = "Nascimento deve ter no máximo 50 caracteres")
    private String nascimento;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ser válido")
    @Size(max = 50, message = "Email deve ter no máximo 50 caracteres")
    private String email;

    @NotBlank(message = "Senha é obrigatória")
    @Size(max = 250, message = "Senha deve ter no máximo 250 caracteres")
    private String password;

    @Size(max = 50, message = "CPF deve ter no máximo 50 caracteres")
    private String cpf;

    @Size(max = 250, message = "Telefone deve ter no máximo 250 caracteres")
    private String telefone;

    @NotBlank(message = "Destinatário é obrigatório")
    @Size(max = 250, message = "Destinatário deve ter no máximo 250 caracteres")
    private String destinatario;

    @NotNull(message = "Role é obrigatório")
    private Integer role;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<EnderecoDTO> enderecos;
}
