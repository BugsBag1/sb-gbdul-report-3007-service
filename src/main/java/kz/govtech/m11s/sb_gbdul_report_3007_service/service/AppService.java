package kz.govtech.m11s.sb_gbdul_report_3007_service.service;

import kz.govtech.m11s.sb_gbdul_report_3007_service.clients.kdp.KdpServiceClient;
import kz.govtech.m11s.sb_gbdul_report_3007_service.clients.kdp.KdpServiceError;
import kz.govtech.m11s.sb_gbdul_report_3007_service.clients.kdp.dto.KdpToken;
import kz.govtech.m11s.sb_gbdul_report_3007_service.config.AppConstants;
import kz.govtech.m11s.sb_gbdul_report_3007_service.dto.RequestDTO;
import kz.govtech.m11s.sb_gbdul_report_3007_service.dto.ResponseDTO;
import kz.govtech.m11s.sb_gbdul_report_3007_service.dto.common.CompanyInfoTypeDTO;
import kz.govtech.m11s.sb_gbdul_report_3007_service.dto.common.InfoAbtTokenDTO;
import kz.govtech.m11s.sb_gbdul_report_3007_service.dto.common.RequestorDTO;
import kz.govtech.m11s.sb_gbdul_report_3007_service.dto.common.TokenInfoDTO;
import kz.govtech.m11s.sb_gbdul_report_3007_service.dto.request.RequestTypeDTO;
import kz.govtech.m11s.sb_gbdul_report_3007_service.errors.Errors;
import kz.govtech.m11s.sb_gbdul_report_3007_service.errors.RemoteError;
import kz.govtech.m11s.syncshepclient.dto.DataResponse;
import kz.govtech.m11s.syncshepclient.web.ws.client.SyncShepClient;
import kz.govtech.m11s.validation.errors.BadRequestException;
import kz.govtech.m11s.validation.errors.NotFoundException;
import kz.govtech.m11s.validation.errors.TerminalException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class AppService {
    private final KdpServiceClient kdpServiceClient;
    private final Converter converter;
    private final SyncShepClient shepClient;
    private final AppConstants appConstants;
    private static final String SERVICE_ID = "GbdulReport3007_KDP";
    private final static String STATUS_SUCCESS = "SCSS001";
    private final static String STATUS_NOT_FOUND = "404";

    public ResponseDTO getReport3007(
        String bin,
        String iin,
        String requestorClientId,
        String requestorDepartmentName
    ){
        String requestorBin = appConstants.getRequestorBin();
        String requsetSystemId = appConstants.getRequestSystemId();
        final KdpToken kdpToken;
        try {
            kdpToken = kdpServiceClient.getKdpToken(SERVICE_ID, iin, requestorClientId, requestorDepartmentName);
        } catch (KdpServiceError.KdpNotFoundError e) {
            throw new BadRequestException("KDP token not found", Errors.KDP_TOKEN_NOT_FOUND);
        }

        InfoAbtTokenDTO infoAbtTokenDTO = new InfoAbtTokenDTO();
        TokenInfoDTO tokenInfoDTO = new TokenInfoDTO();
        List<TokenInfoDTO> tokenInfoDTOList = new ArrayList<>();
        tokenInfoDTO.setCode(kdpToken.getToken());
        tokenInfoDTO.setPublicKey(kdpToken.getPublicKey());
        tokenInfoDTOList.add(tokenInfoDTO);
        infoAbtTokenDTO.setTokens(tokenInfoDTOList);

        CompanyInfoTypeDTO companyInfoTypeDTO = new CompanyInfoTypeDTO();
        companyInfoTypeDTO.setBin(requestorBin);

        RequestorDTO requestorDTO = new RequestorDTO();
        requestorDTO.setOrganization(companyInfoTypeDTO);

        RequestTypeDTO requestTypeDTO = new RequestTypeDTO();
        requestTypeDTO.setBin(bin);
        requestTypeDTO.setRequestor(requestorDTO);
        requestTypeDTO.setInfoAbtToken(infoAbtTokenDTO);

        RequestDTO request = new RequestDTO();
        request.setRequestNumber(UUID.randomUUID().toString());
        request.setDeclarantId(requestorBin);
        request.setRequestDate(LocalDateTime.now());
        request.setRequestSystemId(requsetSystemId);

        request.setBusinessData(requestTypeDTO);

        final DataResponse dataResponse = shepClient.sendMessage(converter.createDataRequest(request));
        if (dataResponse.getStatusCode().equals(STATUS_NOT_FOUND)) {
            throw new NotFoundException(dataResponse.getStatusMessage());
        }

        if (!dataResponse.getStatusCode().equals(STATUS_SUCCESS)) {
            throw new TerminalException(
                dataResponse.getStatusMessage(),
                Errors.REMOTE_SERVICE_ERROR,
                Map.of(
                    "remoteServiceError",
                    new RemoteError(
                        dataResponse.getStatusCode(),
                        dataResponse.getStatusMessage()
                    )
                )
            );
        }

        return converter.processCreateResponse(dataResponse);
    }
}
