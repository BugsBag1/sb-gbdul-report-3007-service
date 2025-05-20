package kz.govtech.m11s.sb_gbdul_report_3007_service.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties
@Getter
@Setter
public class AppConfigurator {
    private String shepServiceId;
    private String shepSignKeyPath;
    private String shepSignKeyType;
    private String shepSignKeyPass;
}
