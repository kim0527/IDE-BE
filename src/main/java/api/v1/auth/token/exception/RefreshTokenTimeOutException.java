package api.v1.auth.token.exception;

public class RefreshTokenTimeOutException extends RuntimeException {
    public RefreshTokenTimeOutException(String message) {
        super(message);
    }
}