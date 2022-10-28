package uz.enterprise.mytex.security;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import uz.enterprise.mytex.response.ApiErrorResponse;

/**
 * @author - 'Zuhriddin Shamsiddionov' at 1:44 PM 10/26/22 on Wednesday in October
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class CustomAuthEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        log.error("Unauthorized error: {}", authException.getMessage());
        ServletOutputStream outputStream = response.getOutputStream();
        ApiErrorResponse failureResponse = ApiErrorResponse.builder()
                .friendlyMessage(authException.getMessage())
                .developerMessage(authException.getMessage())
                .requestPath(request.getRequestURI())
                .build();
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        objectMapper.writeValue(outputStream, failureResponse);
    }
}
