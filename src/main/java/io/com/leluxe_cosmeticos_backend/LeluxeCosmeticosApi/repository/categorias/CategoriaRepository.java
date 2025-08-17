package io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.repository.categorias;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.entities.categorias.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    Optional<Categoria> findByNome(String nome);

    boolean existsByNome(String nome);

    @Query("SELECT c FROM Categoria c WHERE c.nome = :nome AND c.idCategoria != :excludeId")
    Optional<Categoria> findByNomeExcludingId(@Param("nome") String nome, @Param("excludeId") Long excludeId);
}
