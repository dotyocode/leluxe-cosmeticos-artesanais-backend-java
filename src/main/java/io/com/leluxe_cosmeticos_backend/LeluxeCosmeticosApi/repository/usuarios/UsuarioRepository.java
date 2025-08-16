package io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.repository.usuarios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.entities.usuarios.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<Usuario> findByCpf(String cpf);

    boolean existsByCpf(String cpf);

    @Query("SELECT u FROM Usuario u WHERE u.role = :role")
    java.util.List<Usuario> findByRole(@Param("role") Integer role);

    @Query("SELECT u FROM Usuario u LEFT JOIN FETCH u.enderecos WHERE u.idUsuario = :id")
    Optional<Usuario> findByIdWithEnderecos(@Param("id") Long id);
}
