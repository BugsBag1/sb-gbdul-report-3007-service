package kz.govtech.m11s.sb_gbdul_report_3007_service.mapper;

import kz.govtech.m11s.sb_gbdul_report_3007_service.dto.ResponseDTO;
import kz.govtech.m11s.sb_gbdul_report_3007_service.xsd.RequestAndResponse.Response;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ResponseTypeMapper.class, StatusTypeMapper.class})
public interface ResponseMapper {
    @Mapping(source = "businessData", target = "businessData")
    ResponseDTO toDTO(Response response);
}
