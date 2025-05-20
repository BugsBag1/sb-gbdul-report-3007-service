package kz.govtech.m11s.sb_gbdul_report_3007_service.mapper;

import kz.govtech.m11s.sb_gbdul_report_3007_service.dto.RequestDTO;
import kz.govtech.m11s.sb_gbdul_report_3007_service.xsd.RequestAndResponse.Request;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {RequestTypeMapper.class})
public interface RequestMapper {
    @Mapping(source = "businessData", target = "businessData")
    Request map(RequestDTO requestDTO);
}
