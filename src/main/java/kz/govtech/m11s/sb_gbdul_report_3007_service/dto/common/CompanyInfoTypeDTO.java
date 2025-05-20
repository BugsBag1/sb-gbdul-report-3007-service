package kz.govtech.m11s.sb_gbdul_report_3007_service.dto.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CompanyInfoTypeDTO {
    @Schema(description = "БИН")
    private String bin;
    @Schema(description = "Наименование организации на русском языке")
    private String organizationNameRu;
    @Schema(description = "Наименование организации на государственном языке")
    private String organizationNameKz;
}
