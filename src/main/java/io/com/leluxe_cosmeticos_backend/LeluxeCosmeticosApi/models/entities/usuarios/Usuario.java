package io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.entities.usuarios;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.br.CPF;

import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.common.enums.roles.Role;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.entities.enderecos.Endereco;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
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
    @NotNull(message = "Nome é obrigatório")
    @NotBlank(message = "Nome não pode estar vazio")
    @Size(max = 250, message = "Nome deve ter no máximo 250 caracteres")
    private String nome;

    @Column(name = "nascimento")
    @Temporal(TemporalType.DATE)
    @NotNull(message = "Nascimento é obrigatório")
    @Past(message = "Data de nascimento não pode ser futura")
    private Date nascimento;

    @Column(name = "email", length = 50, unique = true)
    @NotNull(message = "Email é obrigatório")
    @NotBlank(message = "Email não pode estar vazio")
    @Email(message = "Email deve ser válido")
    @Size(max = 50, message = "Email deve ter no máximo 50 caracteres")
    private String email;

    @Column(name = "password", length = 250)
    @NotNull(message = "Senha é obrigatória")
    @NotBlank(message = "Senha não pode estar vazia")
    @Size(max = 250, message = "Senha deve ter no máximo 250 caracteres")
    private String password;

    @Column(name = "cpf", length = 11)
    @NotNull(message = "CPF é obrigatório")
    @NotBlank(message = "CPF não pode estar vazio")
    @CPF(message = "CPF deve ser válido")
    @Size(max = 11, message = "CPF deve ter no máximo 11 caracteres")
    private String cpf;

    @Column(name = "telefone", length = 250)
    @NotNull(message = "Telefone é obrigatório")
    @NotBlank(message = "Telefone não pode estar vazio")
    @Size(max = 250, message = "Telefone deve ter no máximo 250 caracteres")
    private String telefone;

    @Column(name = "destinatario", length = 250)
    @NotNull(message = "Destinatário é obrigatório")
    @NotBlank(message = "Destinatário não pode estar vazio")
    @Size(max = 250, message = "Destinatário deve ter no máximo 250 caracteres")
    private String destinatario;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Role é obrigatória")
    private Role role;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Endereco> enderecos = new ArrayList<>();
}