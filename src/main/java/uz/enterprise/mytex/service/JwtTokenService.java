package uz.enterprise.mytex.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import uz.enterprise.mytex.security.CustomUserDetails;
import uz.enterprise.mytex.util.JwtUtils;

/**
 * @author - 'Zuhriddin Shamsiddionov' at 1:46 PM 10/26/22 on Wednesday in October
 */
@Service
@RequiredArgsConstructor
@PropertySource("classpath:application-dev.properties")
public class JwtTokenService {
    @Value("${jwt.access.token.secret}")
    private String secretToken = "U0RGVyQ0MzUzZnNkRyUkXiQlXjxERkhHPE9ZVUslJF4+SkhGR0pUUllVJV4=";
    private final JwtUtils jwtUtils;

    public String generateToken(CustomUserDetails userDetails) {
        return jwtUtils.jwt(userDetails.getUsername(), secretToken);
    }

    public Boolean isValid(String token) {
        return jwtUtils.isTokenValid(token, secretToken);
    }

    public String subject(String token) {
        return jwtUtils.getSubject(token, secretToken);
    }
}
