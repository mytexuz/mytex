package uz.enterprise.mytex.helper;

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
import static uz.enterprise.mytex.util.DateUtil.getTime;

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
        return prepareResponse(MessageKey.FORBIDDEN, HttpStatus.FORBIDDEN);
    }

    public ResponseEntity<ResponseData<String>> userDoesNotExist() {
        return prepareResponse(MessageKey.USER_DOES_NOT_EXIST, HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<ResponseData<String>> accountPending() {
        return prepareResponse(MessageKey.ACCOUNT_PENDING, HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<ResponseData<String>> accountBlocked() {
        return prepareResponse(MessageKey.ACCOUNT_BLOCKED, HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<ResponseData<String>> deviceBlocked() {
        return prepareResponse(MessageKey.DEVICE_BLOCKED, HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<ResponseData<String>> sessionDisabled() {
        return prepareResponse(MessageKey.SESSION_DISABLED, HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<ResponseData<String>> usernameExists() {
        return prepareResponse(MessageKey.USERNAME_EXISTS, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<ResponseData<String>> operationFailed() {
        return prepareResponse(MessageKey.OPERATION_FAILED, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<ResponseData<String>> emailExists() {
        return prepareResponse(MessageKey.EMAIL_EXISTS, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<ResponseData<String>> deviceNotBlocked() {
        return prepareResponse(MessageKey.DEVICE_NOT_BLOCKED, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<ResponseData<String>> accountNotBlocked() {
        return prepareResponse(MessageKey.ACCOUNT_NOT_BLOCKED, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<ResponseData<String>> incorrectPassword() {
        return prepareResponse(MessageKey.INCORRECT_PASSWORD, HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<Object> success(Map<Object, Object> object) {
        return prepareResponse(object, MessageKey.SUCCESS, HttpStatus.OK);
    }

    public ResponseEntity<Object> success(Object object) {
        return prepareResponse(object, MessageKey.SUCCESS);
    }

    public ResponseEntity<ResponseData<String>> success() {
        return prepareResponse(MessageKey.SUCCESS, HttpStatus.OK);
    }

    public ResponseEntity<ResponseData<String>> noDataFound() {
        return prepareResponse(MessageKey.DATA_NOT_FOUND, HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<ResponseData<String>> internalServerError() {
        return prepareResponse(MessageKey.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<ResponseData<String>> invalidData() {
        return prepareResponse(MessageKey.INVALID_DATA);
    }

    public ResponseEntity<ResponseData<String>> invalidFileSize() {
        return prepareResponse(MessageKey.MAX_SIZE, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Object> deviceAlreadyBlocked(String blockedBy) {
        return prepareResponse(blockedBy, MessageKey.DEVICE_ALREADY_BLOCKED);
    }

    public ResponseEntity<Object> accountAlreadyBlocked(String blockedBy) {
        return prepareResponse(blockedBy, MessageKey.ACCOUNT_ALREADY_BLOCKED);
    }

    public ResponseEntity<Object> deviceAlreadyUnBlocked(String unblockedBy) {
        return prepareResponse(unblockedBy, MessageKey.DEVICE_ALREADY_UNBLOCKED);
    }

    public ResponseEntity<Object> accountAlreadyUnBlocked(String unblockedBy) {
        return prepareResponse(unblockedBy, MessageKey.ACCOUNT_ALREADY_UNBLOCKED);
    }

    private ResponseEntity<ResponseData<String>> prepareResponse(String key, HttpStatus status) {
        ResponseData<String> response;
        if (isSuccessStatus(status)) {
            response = new ResponseData<>(localizationService.getMessage(key));
        } else {
            response = new ResponseData<>(null, localizationService.getMessage(key));
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

    private ResponseEntity<Object> prepareResponse(String data, String key) {
        Map<String, Object> result = new HashMap<>();
        result.put("message", localizationService.getMessage(key).formatted(data));
        result.put("timestamp", getTime());
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ResponseData<String>> prepareResponse(String key) {
        return prepareResponse(key, HttpStatus.BAD_REQUEST);
    }

}
