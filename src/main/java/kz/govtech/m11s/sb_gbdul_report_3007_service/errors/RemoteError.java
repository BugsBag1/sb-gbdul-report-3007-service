package kz.govtech.m11s.sb_gbdul_report_3007_service.errors;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RemoteError {
    private String errorCode;
    private String errorMessage;
}
