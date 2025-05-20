package kz.govtech.m11s.sb_gbdul_report_3007_service.mapper;

import kz.govtech.m11s.sb_gbdul_report_3007_service.dto.common.RequestorDTO;
import kz.govtech.m11s.sb_gbdul_report_3007_service.xsd.RequestAndResponse.RequestorType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CompanyInfoTypeMapper.class})
public interface RequestorTypeMapper {
    @Mapping(source = "organization", target = "organization")
    RequestorType map(RequestorDTO requestorDTO);

    @Mapping(source = "organization", target = "organization")
    RequestorDTO toDTO(RequestorType requestorType);

}
