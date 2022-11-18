package uz.enterprise.mytex.exception;

import org.springframework.http.ResponseEntity;
import uz.enterprise.mytex.dto.response.ResponseData;

import java.util.Objects;

public class FileException extends Exception{
    private ResponseEntity<?> response;

    public FileException(String message){
        super(message);
    }

    public FileException(ResponseEntity<?> response){
        super(Objects.requireNonNull((ResponseData<?>) response.getBody()).getMessage());
        this.response = response;
    }

    public ResponseEntity<?> getResponse(){
        return response;
    }
}
