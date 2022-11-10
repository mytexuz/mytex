package uz.enterprise.mytex.exception;

import java.util.Objects;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import uz.enterprise.mytex.dto.response.ResponseData;

public class CustomAuthenticationException extends AuthenticationException {
    private ResponseEntity<?> response;

    public CustomAuthenticationException(String msg) {
        super(msg);
    }

    public CustomAuthenticationException(ResponseEntity<?> response) {
        super(Objects.requireNonNull((ResponseData<?>) response.getBody()).getMessage());
        this.response = response;
    }

    public ResponseEntity<?> getResponse() {
        return response;
    }
}
