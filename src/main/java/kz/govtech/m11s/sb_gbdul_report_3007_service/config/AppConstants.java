package kz.govtech.m11s.sb_gbdul_report_3007_service.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "request-data")
public class AppConstants {
    private String requestorBin;
    private String requestSystemId;

    public String getRequestorBin() {
        return requestorBin;
    }

    public void setRequestorBin(String requestorBin) {
        this.requestorBin = requestorBin;
    }

    public String getRequestSystemId() {
        return requestSystemId;
    }

    public void setRequestSystemId(String requestSystemId) {
        this.requestSystemId = requestSystemId;
    }
}
