package kz.govtech.m11s.sb_gbdul_report_3007_service.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kz.govtech.m11s.sb_gbdul_report_3005_service.dto.common.StatusTypeDTO;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BaseResponseMessageDTO {
    @Schema(description = "Номер запроса на портале")
    private String requestNumber;
    @Schema(description = "ИИН/БИН заявителя")
    private String declarantId;
    @Schema(description = "Дата запроса, в системе иницировавшей запрос")
    private LocalDateTime requestDate;
    @Schema(description = "Идентификатор системы запросившей услугу через ПЭП")
    private String requestSystemId;
    @Schema(description = "Статус ответа")
    private StatusTypeDTO status;
}
