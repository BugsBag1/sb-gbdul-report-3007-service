package kz.govtech.m11s.sb_gbdul_report_3007_service.mapper;

import kz.govtech.m11s.sb_gbdul_report_3007_service.dto.common.OrganizationTypeDTO;
import kz.govtech.m11s.sb_gbdul_report_3007_service.xsd.RequestAndResponse.OrganizationType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {DirectoryTypeMapper.class})
public interface OrganizationTypeMapper {
    OrganizationTypeDTO toDTO(OrganizationType organizationType);
}
