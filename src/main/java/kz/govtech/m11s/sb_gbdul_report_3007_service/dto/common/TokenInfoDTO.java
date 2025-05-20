package kz.govtech.m11s.sb_gbdul_report_3007_service.dto.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class TokenInfoDTO {
    @Schema(description = "Токен безопасности")
    private String code;
    @Schema(description = "Публичный ключ, необходимый для проверки валидности токена безопасности")
    private String publicKey;
}
