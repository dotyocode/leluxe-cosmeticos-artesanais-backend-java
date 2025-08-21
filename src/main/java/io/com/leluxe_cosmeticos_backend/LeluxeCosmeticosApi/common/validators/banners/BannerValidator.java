package io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.common.validators.banners;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.common.exceptions.CampoInvalidoException;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.common.exceptions.OperacaoNaoPermitidaException;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.common.exceptions.RegistroNaoEncontradoException;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.banners.BannerCreateDTO;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.banners.BannerUpdateDTO;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.entities.banners.Banner;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.repository.banners.BannerRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BannerValidator {

    private final BannerRepository bannerRepository;
    private static final List<Integer> POSICOES_VALIDAS = Arrays.asList(1, 2, 3);

    public void validateCreate(BannerCreateDTO dto) {
        validatePosition(dto.getPosition());
        validateBannerLimit(dto.getPosition());
    }

    public void validateUpdate(BannerUpdateDTO dto, Long bannerId) {
        validatePosition(dto.getPosition());

        // Verificar se a posição foi alterada
        Banner existingBanner = bannerRepository.findById(bannerId)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Banner", "ID", bannerId.toString()));

        if (!existingBanner.getPosition().equals(dto.getPosition())) {
            validateBannerLimit(dto.getPosition());
        }
    }

    private void validatePosition(Integer position) {
        if (position == null || !POSICOES_VALIDAS.contains(position)) {
            throw new CampoInvalidoException("position", position != null ? position.toString() : "null",
                    "Posição deve ser 1, 2 ou 3");
        }
    }

    private void validateBannerLimit(Integer position) {
        long currentCount = bannerRepository.countBannersByPosition(position);
        int maxBanners = position == 2 ? 2 : 3;

        if (currentCount >= maxBanners) {
            throw new OperacaoNaoPermitidaException("criar banner",
                    String.format("Já existem %d banners na posição %d. Limite máximo atingido.", maxBanners,
                            position));
        }
    }

    public void validateExists(Long id) {
        if (!bannerRepository.existsById(id)) {
            throw new RegistroNaoEncontradoException("Banner", "ID", id.toString());
        }
    }
}
