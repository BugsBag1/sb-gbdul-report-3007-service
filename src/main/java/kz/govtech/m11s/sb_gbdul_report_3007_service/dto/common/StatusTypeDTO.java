package kz.govtech.m11s.sb_gbdul_report_3007_service.dto.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class StatusTypeDTO {
    @Schema(description = "Код")
    private String code;
    @Schema(description = "Значение на русском языке")
    private String nameRu;
    @Schema(description = "Значение на государственном языке")
    private String nameKz;
}
