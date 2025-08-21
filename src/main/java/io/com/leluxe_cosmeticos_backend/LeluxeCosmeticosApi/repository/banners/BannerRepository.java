package io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.repository.banners;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.entities.banners.Banner;

@Repository
public interface BannerRepository extends JpaRepository<Banner, Long> {

    @Query("SELECT b FROM Banner b WHERE b.position = :position ORDER BY b.createdAt ASC")
    List<Banner> findBannersByPosition(@Param("position") Integer position);

    @Query("SELECT COUNT(b) FROM Banner b WHERE b.position = :position")
    long countBannersByPosition(@Param("position") Integer position);

    @Query("SELECT b FROM Banner b ORDER BY b.position ASC, b.createdAt ASC")
    List<Banner> findAllOrderedByPositionAndCreatedAt();
}
