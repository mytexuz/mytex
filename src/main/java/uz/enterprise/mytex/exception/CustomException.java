package uz.enterprise.mytex.exception;

import java.util.Objects;

import org.springframework.http.ResponseEntity;
import uz.enterprise.mytex.dto.response.ResponseData;

public class CustomException extends RuntimeException{
    private ResponseEntity<?> response;

    public CustomException(String message){
        super(message);
    }

    public CustomException(ResponseEntity<?> response){
        super(Objects.requireNonNull((ResponseData<?>) response.getBody()).getMessage());
        this.response = response;
    }

    public ResponseEntity<?> getResponse(){
        return response;
    }
}
