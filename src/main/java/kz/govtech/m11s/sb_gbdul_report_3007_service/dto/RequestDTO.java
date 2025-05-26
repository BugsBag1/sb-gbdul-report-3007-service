package kz.govtech.m11s.sb_gbdul_report_3007_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import kz.govtech.m11s.sb_gbdul_report_3007_service.dto.request.BaseRequestMessageDTO;
import kz.govtech.m11s.sb_gbdul_report_3007_service.dto.request.RequestTypeDTO;
import lombok.Data;

@Data
public class RequestDTO extends BaseRequestMessageDTO {
    @Schema(description = "Бизнес данные запроса")
    private RequestTypeDTO businessData;
}
