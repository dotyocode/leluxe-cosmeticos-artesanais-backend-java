package io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.usuarios;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioUpdateDTO {

    @Size(max = 250, message = "Nome deve ter no máximo 250 caracteres")
    private String nome;

    @Size(max = 50, message = "Nascimento deve ter no máximo 50 caracteres")
    private String nascimento;

    @Email(message = "Email deve ser válido")
    @Size(max = 50, message = "Email deve ter no máximo 50 caracteres")
    private String email;

    @Size(max = 250, message = "Senha deve ter no máximo 250 caracteres")
    private String password;

    @Size(max = 50, message = "CPF deve ter no máximo 50 caracteres")
    private String cpf;

    @Size(max = 250, message = "Telefone deve ter no máximo 250 caracteres")
    private String telefone;

    @Size(max = 250, message = "Destinatário deve ter no máximo 250 caracteres")
    private String destinatario;

    private Integer role;
}
