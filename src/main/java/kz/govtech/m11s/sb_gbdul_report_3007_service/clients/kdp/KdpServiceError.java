package kz.govtech.m11s.sb_gbdul_report_3007_service.clients.kdp;

public abstract class KdpServiceError extends Exception {

    protected KdpServiceError() {
        super();
    }

    protected KdpServiceError(String message) {
        super(message);
    }

    protected KdpServiceError(String message, Throwable cause) {
        super(message, cause);
    }

    public static class KdpNotFoundError extends KdpServiceError {

    }
}
