package io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.entities.enderecos;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.entities.usuarios.Usuario;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "enderecos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_endereco")
    private Long idEndereco;

    @Column(name = "cep", length = 50)
    @Size(max = 50, message = "CEP deve ter no máximo 50 caracteres")
    private String cep;

    @Column(name = "endereco", length = 250)
    @Size(max = 250, message = "Endereço deve ter no máximo 250 caracteres")
    private String endereco;

    @Column(name = "numero")
    private Integer numero;

    @Column(name = "complemento", length = 50)
    @Size(max = 50, message = "Complemento deve ter no máximo 50 caracteres")
    private String complemento;

    @Column(name = "bairro", length = 50)
    @Size(max = 50, message = "Bairro deve ter no máximo 50 caracteres")
    private String bairro;

    @Column(name = "cidade", length = 50)
    @Size(max = 50, message = "Cidade deve ter no máximo 50 caracteres")
    private String cidade;

    @Column(name = "pais", length = 50)
    @Size(max = 50, message = "País deve ter no máximo 50 caracteres")
    private String pais;

    @Column(name = "estado", length = 50)
    @Size(max = 50, message = "Estado deve ter no máximo 50 caracteres")
    private String estado;

    @Column(name = "is_principal")
    private Boolean isPrincipal = false;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    @NotNull(message = "Usuário é obrigatório")
    private Usuario usuario;
}