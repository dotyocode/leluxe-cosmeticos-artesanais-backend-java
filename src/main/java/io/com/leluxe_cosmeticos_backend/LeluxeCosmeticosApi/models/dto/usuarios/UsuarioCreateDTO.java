package io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.usuarios;

import java.util.Date;

import org.hibernate.validator.constraints.br.CPF;

import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.common.annotations.ValidSenha;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.common.annotations.ValidTelefone;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.common.enums.roles.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioCreateDTO {

    @NotNull(message = "Nome é obrigatório")
    @NotBlank(message = "Nome não pode estar vazio")
    @Size(max = 250, message = "Nome deve ter no máximo 250 caracteres")
    private String nome;

    @NotNull(message = "Nascimento é obrigatório")
    @Past(message = "Data de nascimento não pode ser futura")
    private Date nascimento;

    @NotNull(message = "Email é obrigatório")
    @NotBlank(message = "Email não pode estar vazio")
    @Email(message = "Email deve ser válido")
    @Size(max = 50, message = "Email deve ter no máximo 50 caracteres")
    private String email;

    @ValidSenha
    private String password;

    @NotNull(message = "CPF é obrigatório")
    @NotBlank(message = "CPF não pode estar vazio")
    @CPF(message = "CPF deve ser válido")
    @Size(max = 11, message = "CPF deve ter no máximo 11 caracteres")
    private String cpf;

    @ValidTelefone
    private String telefone;

    @NotNull(message = "Destinatário é obrigatório")
    @NotBlank(message = "Destinatário não pode estar vazio")
    @Size(max = 250, message = "Destinatário deve ter no máximo 250 caracteres")
    private String destinatario;

    @NotNull(message = "Role é obrigatória")
    private Role role;
}
