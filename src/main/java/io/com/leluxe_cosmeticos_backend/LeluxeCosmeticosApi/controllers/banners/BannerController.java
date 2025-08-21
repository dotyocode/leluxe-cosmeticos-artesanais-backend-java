package io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.controllers.banners;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.common.exceptions.GlobalExceptionHandler.SuccessResponse;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.banners.BannerCreateDTO;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.banners.BannerDTO;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.banners.BannerUpdateDTO;
import io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.services.banners.BannerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("banners")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class BannerController {

    private final BannerService bannerService;

    @GetMapping
    public ResponseEntity<List<BannerDTO>> findAll() {
        List<BannerDTO> banners = bannerService.findAllOrderedByPosition();
        return ResponseEntity.ok(banners);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BannerDTO> findById(@PathVariable Long id) {
        BannerDTO banner = bannerService.findByIdOrThrow(id);
        return ResponseEntity.ok(banner);
    }

    @GetMapping("/posicao/{position}")
    public ResponseEntity<List<BannerDTO>> findByPosition(@PathVariable Integer position) {
        List<BannerDTO> banners = bannerService.findByPosition(position);
        return ResponseEntity.ok(banners);
    }

    @PostMapping
    public ResponseEntity<SuccessResponse<BannerDTO>> create(@Valid @RequestBody BannerCreateDTO bannerCreateDTO) {
        BannerDTO createdBanner = bannerService.create(bannerCreateDTO);

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
        String path = request.getRequestURI();

        SuccessResponse<BannerDTO> response = SuccessResponse.of(
                createdBanner,
                "Banner criado com sucesso",
                HttpStatus.CREATED.value(),
                path);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponse<BannerDTO>> update(@PathVariable Long id,
            @Valid @RequestBody BannerUpdateDTO dto) {
        BannerDTO updatedBanner = bannerService.update(id, dto);

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
        String path = request.getRequestURI();

        SuccessResponse<BannerDTO> response = SuccessResponse.of(
                updatedBanner,
                "Banner atualizado com sucesso",
                HttpStatus.OK.value(),
                path);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse<Void>> delete(@PathVariable Long id) {
        bannerService.delete(id);

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
        String path = request.getRequestURI();

        SuccessResponse<Void> response = SuccessResponse.of(
                null,
                "Banner excluído com sucesso",
                HttpStatus.OK.value(),
                path);

        return ResponseEntity.ok(response);
    }
}
