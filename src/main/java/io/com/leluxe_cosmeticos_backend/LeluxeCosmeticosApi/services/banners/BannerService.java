package io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.services.banners;

import java.util.List;

import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.banners.BannerCreateDTO;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.banners.BannerDTO;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.banners.BannerUpdateDTO;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.entities.banners.Banner;

public interface BannerService {

    List<BannerDTO> findAll();

    List<BannerDTO> findAllOrderedByPosition();

    BannerDTO findByIdOrThrow(Long id);

    Banner findBannerEntityByIdOrThrow(Long id);

    BannerDTO create(BannerCreateDTO dto);

    BannerDTO update(Long id, BannerUpdateDTO dto);

    void delete(Long id);

    boolean existsById(Long id);

    List<BannerDTO> findByPosition(Integer position);
}
