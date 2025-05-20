package kz.govtech.m11s.sb_gbdul_report_3007_service.mapper;

import kz.govtech.m11s.sb_gbdul_report_3007_service.dto.response.ResponseTypeDTO;
import kz.govtech.m11s.sb_gbdul_report_3007_service.xsd.RequestAndResponse.ResponseType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {OrganizationTypeMapper.class, RequestorTypeMapper.class})
public interface ResponseTypeMapper {
    @Mapping(source = "organization", target = "organization")
    @Mapping(source = "requestor", target = "requestor")
    ResponseTypeDTO toDTO(ResponseType responseType);
}
