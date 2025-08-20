package io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.repository.subcategorias;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.entities.subcategorias.SubCategoria;

@Repository
public interface SubCategoriaRepository extends JpaRepository<SubCategoria, Long> {

    Optional<SubCategoria> findByNome(String nome);

    boolean existsByNome(String nome);

    @Query("SELECT s FROM SubCategoria s WHERE s.nome = :nome AND s.idSubcategoria != :excludeId")
    Optional<SubCategoria> findByNomeExcludingId(@Param("nome") String nome, @Param("excludeId") Long excludeId);
}
