package io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.services.impl.banners;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.common.exceptions.RegistroNaoEncontradoException;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.common.mapper.banners.BannerMapper;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.common.validators.banners.BannerValidator;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.banners.BannerCreateDTO;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.banners.BannerDTO;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.banners.BannerUpdateDTO;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.entities.banners.Banner;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.repository.banners.BannerRepository;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.services.banners.BannerService;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class BannerServiceImpl implements BannerService {

    private final BannerRepository bannerRepository;
    private final BannerMapper bannerMapper;
    private final BannerValidator bannerValidator;

    @Override
    @Transactional(readOnly = true)
    public List<BannerDTO> findAll() {
        List<Banner> banners = bannerRepository.findAll();
        return banners.stream()
                .map(bannerMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BannerDTO> findAllOrderedByPosition() {
        List<Banner> banners = bannerRepository.findAllOrderedByPositionAndCreatedAt();
        return banners.stream()
                .map(bannerMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public BannerDTO findByIdOrThrow(Long id) {
        Banner banner = findBannerEntityByIdOrThrow(id);
        return bannerMapper.toDTO(banner);
    }

    @Override
    @Transactional(readOnly = true)
    public Banner findBannerEntityByIdOrThrow(Long id) {
        return bannerRepository.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Banner", "ID", id.toString()));
    }

    @Override
    public BannerDTO create(BannerCreateDTO dto) {
        bannerValidator.validateCreate(dto);

        Banner banner = bannerMapper.toEntity(dto);
        Banner savedBanner = bannerRepository.save(banner);

        return bannerMapper.toDTO(savedBanner);
    }

    @Override
    public BannerDTO update(Long id, BannerUpdateDTO dto) {
        bannerValidator.validateExists(id);
        bannerValidator.validateUpdate(dto, id);

        Banner banner = findBannerEntityByIdOrThrow(id);
        bannerMapper.updateEntityFromUpdateDTO(dto, banner);

        Banner updatedBanner = bannerRepository.save(banner);
        return bannerMapper.toDTO(updatedBanner);
    }

    @Override
    public void delete(Long id) {
        bannerValidator.validateExists(id);
        bannerRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return bannerRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BannerDTO> findByPosition(Integer position) {
        List<Banner> banners = bannerRepository.findBannersByPosition(position);
        return banners.stream()
                .map(bannerMapper::toDTO)
                .collect(Collectors.toList());
    }
}
