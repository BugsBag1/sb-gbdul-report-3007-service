package kz.govtech.m11s.sb_gbdul_report_3007_service.dto.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class InfoAbtTokenDTO {
    @Schema(description = "Информация о токене")
    private List<TokenInfoDTO> tokens;
}
