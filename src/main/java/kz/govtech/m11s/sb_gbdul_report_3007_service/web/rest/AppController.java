package kz.govtech.m11s.sb_gbdul_report_3007_service.web.rest;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import kz.govtech.m11s.sb_gbdul_report_3007_service.dto.ResponseDTO;
import kz.govtech.m11s.sb_gbdul_report_3007_service.service.AppService;
import kz.govtech.m11s.sb_gbdul_report_3007_service.utils.JwtKeyCloakTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.parsing.Problem;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Size;

@RestController
@RequestMapping("${api-prefix}/gbdul-report-3007-kdp")
@RequiredArgsConstructor
public class AppController {
    private final AppService appService;

    @Operation(summary = "Сервис получения справки по услуге «Справка о всех регистрационных действиях ЮЛ» с КДП")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation"),
        @ApiResponse(
            responseCode = "400",
            description = "Bad request\n" +
                "\n" +
                "**Типы ошибок, которые может вернуть API**\n" +
                "\n" +
                "| Атрибут `type`                    |  Причина                                      |\n" +
                "| --------------------------------- | ----------------------------------------------|\n" +
                "| `kdp-token-not-found`             |  Kdp токен не найден                           |",
            content = {@Content(schema = @Schema(implementation = Problem.class), mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE)}
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Данные не найдены\n" +
                "\n" +
                "**Типы ошибок, которые может вернуть API**\n" +
                "\n" +
                "| Атрибут `type`                    | Причина                                      |\n" +
                "| --------------------------------- | ---------------------------------------------|\n" +
                "| `data-not-found`                  | Данные не найдены                            |",
            content = {@Content(schema = @Schema(implementation = Problem.class), mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE)}
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error\n" +
                "\n" +
                "**Типы ошибок, которые может вернуть API**\n" +
                "\n" +
                "| Атрибут `type`                   | Причина                                                     |\n" +
                "| ---------------------------------| ------------------------------------------------------------|\n" +
                "| `remote-service-error`           | Ошибка на стороне гос. органа, см. поле `remoteServiceError`|\n" +
                "| `shep-error`                     | Ошибка ВШЭП, см. поле `shepError`                           |\n" +
                "| `shep-unknown-error`             | Неизвестная ошибка ВШЭП                                     |\n" +
                "| `govtech-transport-error`        | Транспортная ошибка GovTech                                 |\n" +
                "| `unknown-error`                  | Техническая ошибка                                          |",
            content = {@Content(schema = @Schema(implementation = Problem.class), mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE)}
        )
    })

    @GetMapping
    public ResponseDTO getReport3005(
        @Parameter(description = "БИН организации, для которой запрашивается справка") @Size(min = 12, max = 12)
        @RequestParam String bin,
        @Parameter(description = "ИИН физического лица (необязательный параметр)", required = false)
        @Size(min = 12, max = 12)
        @RequestParam(required = false) String iin,
        @Parameter(hidden = true) @RequestHeader("Authorization") final String serviceToken
    ) {
        var claimsData = JwtKeyCloakTokenUtils.parseJwtKeyCloakToken(serviceToken);
        return appService.getReport3007(bin, iin, claimsData.getClientId(), claimsData.getDepartmentName());
    }
}
