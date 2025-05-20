package kz.govtech.m11s.sb_gbdul_report_3007_service.mapper;

import kz.govtech.m11s.sb_gbdul_report_3007_service.dto.common.CompanyInfoTypeDTO;
import kz.govtech.m11s.sb_gbdul_report_3007_service.xsd.RequestAndResponse.CompanyInfoType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CompanyInfoTypeMapper {
    @Mapping(source = "bin", target = "BIN")
    CompanyInfoType map(CompanyInfoTypeDTO companyInfoTypeDTO);

    @Mapping(source = "BIN", target = "bin")
    CompanyInfoTypeDTO toDTO(CompanyInfoType companyInfoType);

}
