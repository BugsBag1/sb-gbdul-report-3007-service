package kz.govtech.m11s.sb_gbdul_report_3007_service.dto.common;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;

public class OrganizationTypeDTO {
    @Schema(description = "1-юрлицо 2-филиал 3-представительство")
    private Integer orgFormCode;
    @Schema(description = "БИН организации")
    private String bin;
    @Schema(description = "Полное наименование организации на русском языке")
    private String organizationNameRu;
    @Schema(description = "Полное наименование организации на государственном языке")
    private String organizationNameKz;
    @Schema(description = "Орган юстиции, зарегистрировавший организацию")
    private DirectoryTypeDTO registrationDepartment;
    @Schema(description = "Дата последнего регистрационного действия")
    private LocalDate registrationLastDate;
    @Schema(description = "Основания перерегистрации или изменения сведений")
    private List<DirectoryTypeDTO> registrationLastBases;
    @Schema(description = "Количество учредителей фл")
    private int countFlFounders;
    @Schema(description = "Количество учредителей юл")
    private int countUlFounders;
}
