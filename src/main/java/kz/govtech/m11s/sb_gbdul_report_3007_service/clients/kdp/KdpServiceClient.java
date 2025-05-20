package kz.govtech.m11s.sb_gbdul_report_3007_service.clients.kdp;

import kz.govtech.m11s.sb_gbdul_report_3007_service.clients.kdp.dto.KdpToken;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class KdpServiceClient {
    private final String baseUrl;
    private final RestTemplate restTemplate;

    public KdpServiceClient(
        @Value("${clients.kdp-service.base-url}") final String baseUrl,
        final RestTemplate restTemplate
    ) {
        this.baseUrl = baseUrl;
        this.restTemplate = restTemplate;
    }

    public KdpToken getKdpToken(
        @NonNull final String serviceId,
        @NonNull final String iin,
        @NonNull final String requestorClientId,
        @NonNull final String requestorDepartmentName
    ) throws KdpServiceError.KdpNotFoundError {
        try {
            ResponseEntity<KdpToken> responseEntity = restTemplate.getForEntity(
                baseUrl + "/api/v1/private/kdp-tokens/" +
                    "?serviceId=" + serviceId +
                    "&iin=" + iin +
                    "&kdpTokenIssuerClientId=" + requestorClientId +
                    "&kdpTokenIssuerDepartmentName=" + requestorDepartmentName,
                KdpToken.class
            );
            return responseEntity.getBody();
        } catch (HttpClientErrorException.NotFound ex) {
            throw new KdpServiceError.KdpNotFoundError();
        }
    }
}
