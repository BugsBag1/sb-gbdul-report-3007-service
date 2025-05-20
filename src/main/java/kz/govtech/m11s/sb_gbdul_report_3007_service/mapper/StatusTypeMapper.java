package kz.govtech.m11s.sb_gbdul_report_3007_service.mapper;

import kz.govtech.m11s.sb_gbdul_report_3007_service.dto.common.StatusTypeDTO;
import kz.govtech.m11s.sb_gbdul_report_3007_service.xsd.RequestAndResponse.StatusType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StatusTypeMapper {
    StatusTypeDTO toDTO(StatusType statusType);
}
