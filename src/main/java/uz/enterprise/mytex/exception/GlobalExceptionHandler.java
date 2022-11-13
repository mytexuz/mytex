package uz.enterprise.mytex.exception;

import javax.naming.SizeLimitExceededException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import liquibase.repackaged.org.apache.commons.collections4.map.HashedMap;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import uz.enterprise.mytex.helper.ResponseHelper;
import uz.enterprise.mytex.service.LocalizationService;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private final ResponseHelper responseHelper;
    private final LocalizationService localizationService;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGenericException(final Exception e) {
        logger.error(getStackTrace(e), e.getMessage());
        return responseHelper.internalServerError();
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentialsException(final BadCredentialsException e) {
        logger.error(getStackTrace(e), e.getMessage());
        return responseHelper.incorrectPassword();
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(final AccessDeniedException e) {
        logger.error(getStackTrace(e), e.getMessage());
        return responseHelper.unauthorized();
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<?> handleMaxUploadSizeExceededException(final MaxUploadSizeExceededException e) {
        logger.error(getStackTrace(e), e.getMessage());
        return responseHelper.invalidFileSize();
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> handleCustomException(final CustomException e) {
        return e.getResponse();
    }

    @ExceptionHandler(CustomAuthenticationException.class)
    public ResponseEntity<?> handleCustomAuthenticationException(final CustomAuthenticationException e) {
        return e.getResponse();
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e,
                                                                  @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatus status,
                                                                  @NonNull WebRequest request) {
        Map<Object, Object> validation = new HashedMap<>();
        e.getBindingResult().getFieldErrors()
                .forEach(error -> {
                    String message = localizationService.getMessage(error.getDefaultMessage());
                    String field = error.getField();
                    validation.put(field, message);
                });
        return responseHelper.prepareValidationResponse(validation);
    }


    private String getStackTrace(Exception e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }
}
