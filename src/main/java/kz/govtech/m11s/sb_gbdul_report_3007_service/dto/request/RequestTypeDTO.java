package kz.govtech.m11s.sb_gbdul_report_3007_service.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import kz.govtech.m11s.sb_gbdul_report_3007_service.dto.common.InfoAbtTokenDTO;
import kz.govtech.m11s.sb_gbdul_report_3007_service.dto.common.RequestorDTO;
import lombok.Data;

@Data
public class RequestTypeDTO {
    @Schema(description = "БИН юрлица, филиала или представительства")
    private String bin;
    @Schema(description = "Заявитель (используется для отображения в выходном документе на ПЭП)")
    private RequestorDTO requestor;
    @Schema(description = "Информация о токене")
    private InfoAbtTokenDTO infoAbtToken;
}
