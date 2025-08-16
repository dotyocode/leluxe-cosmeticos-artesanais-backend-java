package io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.repository.enderecos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.entities.enderecos.Endereco;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

    List<Endereco> findByUsuarioIdUsuario(Long idUsuario);

    @Query("SELECT e FROM Endereco e WHERE e.usuario.idUsuario = :idUsuario AND e.isPrincipal = true")
    java.util.Optional<Endereco> findEnderecoPrincipalByUsuarioId(@Param("idUsuario") Long idUsuario);

    List<Endereco> findByUsuarioIdUsuarioAndIsPrincipalTrue(Long idUsuario);

    List<Endereco> findByCep(String cep);

    @Query("SELECT e FROM Endereco e WHERE LOWER(e.cidade) LIKE LOWER(CONCAT('%', :cidade, '%'))")
    List<Endereco> findByCidadeContainingIgnoreCase(@Param("cidade") String cidade);

    List<Endereco> findByEstado(String estado);

    @Query("SELECT e FROM Endereco e WHERE LOWER(e.bairro) LIKE LOWER(CONCAT('%', :bairro, '%'))")
    List<Endereco> findByBairroContainingIgnoreCase(@Param("bairro") String bairro);

    @Query("SELECT COUNT(e) FROM Endereco e WHERE e.usuario.idUsuario = :idUsuario")
    long countByUsuarioId(@Param("idUsuario") Long idUsuario);

}
