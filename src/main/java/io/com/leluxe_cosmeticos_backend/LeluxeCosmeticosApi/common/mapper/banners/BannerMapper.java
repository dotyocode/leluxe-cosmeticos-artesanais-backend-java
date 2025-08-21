package io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.common.mapper.banners;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.banners.BannerCreateDTO;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.banners.BannerDTO;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.banners.BannerUpdateDTO;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.entities.banners.Banner;

@Mapper(componentModel = "spring")
public interface BannerMapper {

    @Mapping(target = "idBanner", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Banner toEntity(BannerCreateDTO dto);

    @Mapping(target = "idBanner", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromUpdateDTO(BannerUpdateDTO dto, @MappingTarget Banner banner);

    BannerDTO toDTO(Banner entity);
}
