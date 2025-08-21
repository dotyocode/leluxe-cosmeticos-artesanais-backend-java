package io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi.models.dto.banners;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BannerDTO {

    private Long idBanner;
    private String imgUrl;
    private String title;
    private Integer position;
    private String redirectUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
