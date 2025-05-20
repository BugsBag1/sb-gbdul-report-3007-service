package kz.govtech.m11s.sb_gbdul_report_3007_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import kz.govtech.m11s.sb_gbdul_report_3007_service.dto.response.BaseResponseMessageDTO;

public class ResponseDTO extends BaseResponseMessageDTO {
    @Schema(description = "Бизнес данные ответа")
    private String businessData;
}
