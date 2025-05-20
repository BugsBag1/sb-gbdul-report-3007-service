package kz.govtech.m11s.sb_gbdul_report_3007_service.mapper;

import kz.govtech.m11s.sb_gbdul_report_3007_service.dto.common.DirectoryTypeDTO;
import kz.govtech.m11s.sb_gbdul_report_3007_service.xsd.RequestAndResponse.DirectoryType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DirectoryTypeMapper {
    DirectoryTypeDTO toDTO(DirectoryType directoryType);
}
