package uz.enterprise.mytex.handler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import uz.enterprise.mytex.exceptions.UserNotFoundException;
import uz.enterprise.mytex.response.ApiErrorResponse;
import uz.enterprise.mytex.response.ApiResponse;

/**
 * @author - 'Zuhriddin Shamsiddionov' at 11:17 AM 10/27/22 on Thursday in October
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ApiResponse<ApiErrorResponse> handle404(UserNotFoundException e, HttpServletRequest request) {
        return new ApiResponse<>(ApiErrorResponse.builder()
                .friendlyMessage(e.getMessage())
                .developerMessage(e.getLocalizedMessage())
                .requestPath(request.getRequestURL().toString())
                .build(), HttpStatus.NOT_FOUND);
    }
}
