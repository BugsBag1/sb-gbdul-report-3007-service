package kz.govtech.m11s.sb_gbdul_report_3007_service.mapper;

import kz.govtech.m11s.sb_gbdul_report_3007_service.dto.common.InfoAbtTokenDTO;
import kz.govtech.m11s.sb_gbdul_report_3007_service.dto.common.TokenInfoDTO;
import kz.govtech.m11s.sb_gbdul_report_3007_service.dto.request.RequestTypeDTO;
import kz.govtech.m11s.sb_gbdul_report_3007_service.xsd.RequestAndResponse.InfoAbtToken;
import kz.govtech.m11s.sb_gbdul_report_3007_service.xsd.RequestAndResponse.RequestType;
import kz.govtech.m11s.sb_gbdul_report_3007_service.xsd.RequestAndResponse.TokenInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {RequestorTypeMapper.class})
public interface RequestTypeMapper {

    @Mapping(source = "requestor", target = "requestor")
    @Mapping(source = "bin", target = "BIN")
    RequestType map(RequestTypeDTO requestTypeDTO);

    InfoAbtToken mapInfoAbtToken(InfoAbtTokenDTO infoAbtTokenDTO);

    TokenInfo mapTokenInfo(TokenInfoDTO tokenInfoDTO);

    List<TokenInfo> mapTokenInfoList(List<TokenInfoDTO> tokenInfoDTOList);
}
