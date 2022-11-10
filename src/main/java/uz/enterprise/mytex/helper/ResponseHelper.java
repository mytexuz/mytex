package uz.enterprise.mytex.helper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import uz.enterprise.mytex.constant.MessageKey;
import uz.enterprise.mytex.dto.response.ResponseData;
import uz.enterprise.mytex.service.LocalizationService;

@Component
public class ResponseHelper {
    private final LocalizationService localizationService;

    public ResponseHelper(@Lazy LocalizationService localizationService) {
        this.localizationService = localizationService;
    }

    static public boolean isSuccessStatus(HttpStatus status) {
        List<HttpStatus> successStatuses = new ArrayList<>() {{
            add(HttpStatus.OK);
            add(HttpStatus.ACCEPTED);
            add(HttpStatus.CREATED);
            add(HttpStatus.RESET_CONTENT);
        }};

        return successStatuses.contains(status);
    }

    public ResponseEntity<ResponseData<String>> unauthorized() {
        return prepareResponse(localizationService.getMessage(MessageKey.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<ResponseData<String>> forbidden() {
        return prepareResponse(localizationService.getMessage(MessageKey.FORBIDDEN), HttpStatus.FORBIDDEN);
    }

    public ResponseEntity<ResponseData<String>> userDoesNotExist() {
        return prepareResponse(localizationService.getMessage(MessageKey.USER_DOES_NOT_EXIST), HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<ResponseData<String>> accountPending() {
        return prepareResponse(localizationService.getMessage(MessageKey.ACCOUNT_PENDING), HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<ResponseData<String>> accountBlocked() {
        return prepareResponse(localizationService.getMessage(MessageKey.ACCOUNT_BLOCKED), HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<ResponseData<String>> usernameExists() {
        return prepareResponse(localizationService.getMessage(MessageKey.USERNAME_EXISTS), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<ResponseData<String>> emailExists() {
        return prepareResponse(localizationService.getMessage(MessageKey.EMAIL_EXISTS), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<ResponseData<String>> incorrectPassword() {
        return prepareResponse(localizationService.getMessage(MessageKey.INCORRECT_PASSWORD), HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<Object> successWithObject(Map<Object, Object> object) {
        return prepareResponse(object, localizationService.getMessage(MessageKey.SUCCESS), HttpStatus.OK);
    }

    public ResponseEntity<Object> successWithObject(Object object) {
        return prepareResponse(object, localizationService.getMessage(MessageKey.SUCCESS));
    }

    public ResponseEntity<ResponseData<String>> successWithoutObject() {
        return prepareResponse(localizationService.getMessage(MessageKey.SUCCESS), HttpStatus.OK);
    }

    public ResponseEntity<ResponseData<String>> noDataFound() {
        return prepareResponse(localizationService.getMessage(MessageKey.DATA_NOT_FOUND), HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<ResponseData<String>> internalServerError() {
        return prepareResponse(localizationService.getMessage(MessageKey.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<ResponseData<String>> invalidData() {
        return prepareResponse(localizationService.getMessage(MessageKey.INVALID_DATA));
    }

    public ResponseEntity<ResponseData<String>> invalidFileSize() {
        return prepareResponse(localizationService.getMessage(MessageKey.MAX_SIZE), HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ResponseData<String>> prepareResponse(String message, HttpStatus status) {
        ResponseData<String> response;
        if (isSuccessStatus(status)) {
            response = new ResponseData<>(message);
        } else {
            response = new ResponseData<>(null, message);
        }
        response.setTimestamp(getTime());
        return new ResponseEntity<>(response, status);
    }


    public ResponseEntity<Object> prepareValidationResponse(Map<Object, Object> object) {
        return prepareResponse(object, MessageKey.INVALID_DATA, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Object> prepareResponse(Map<Object, Object> data, String key, HttpStatus status) {
        Map<String, Object> result = new HashMap<>();
        result.put("data", data);
        result.put("message", localizationService.getMessage(key));
        result.put("timestamp", getTime());
        return new ResponseEntity<>(result, status);
    }

    private ResponseEntity<Object> prepareResponse(Object data, String key) {
        Map<String, Object> result = new HashMap<>();
        result.put("data", data);
        result.put("message", localizationService.getMessage(key));
        result.put("timestamp", getTime());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    private ResponseEntity<ResponseData<String>> prepareResponse(String message) {
        return prepareResponse(message, HttpStatus.BAD_REQUEST);
    }

    public String getTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        return now.format(format);
    }
}
