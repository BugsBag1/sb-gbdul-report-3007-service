package kz.govtech.m11s.sb_gbdul_report_3007_service.dto.request;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class BaseRequestMessageDTO implements Serializable {
    private String requestNumber;
    private String declarantId;
    private LocalDateTime requestDate;
    private String requestSystemId;
}
