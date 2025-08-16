package io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.entities.usuarios;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.entities.enderecos.Endereco;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "nome", length = 250)
    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 250, message = "Nome deve ter no máximo 250 caracteres")
    private String nome;

    @Column(name = "nascimento", length = 50)
    @Size(max = 50, message = "Nascimento deve ter no máximo 50 caracteres")
    private String nascimento;

    @Column(name = "email", length = 50, unique = true)
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ser válido")
    @Size(max = 50, message = "Email deve ter no máximo 50 caracteres")
    private String email;

    @Column(name = "password", length = 250)
    @NotBlank(message = "Senha é obrigatória")
    @Size(max = 250, message = "Senha deve ter no máximo 250 caracteres")
    private String password;

    @Column(name = "cpf", length = 50)
    @Size(max = 50, message = "CPF deve ter no máximo 50 caracteres")
    private String cpf;

    @Column(name = "telefone", length = 250)
    @Size(max = 250, message = "Telefone deve ter no máximo 250 caracteres")
    private String telefone;

    @Column(name = "destinatario", length = 250)
    @NotBlank(message = "Destinatário é obrigatório")
    @Size(max = 250, message = "Destinatário deve ter no máximo 250 caracteres")
    private String destinatario;

    @Column(name = "role")
    @NotNull(message = "Role é obrigatório")
    private Integer role;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Endereco> enderecos = new ArrayList<>();
}