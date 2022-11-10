package uz.enterprise.mytex.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.enterprise.mytex.util.JwtUtil;

@Service
@RequiredArgsConstructor
public class JwtTokenService {
    private final JwtUtil jwtUtil;
    private final PropertyService propertyService;

    public String generateToken(String subject) {
        return jwtUtil.jwt(subject, getTokenSecret());
    }

    public Boolean isValid(String token) {
        return jwtUtil.isTokenValid(token, getTokenSecret());
    }

    public String subject(String token) {
        return jwtUtil.getSubject(token, getTokenSecret());
    }

    private String getTokenSecret() {
        return propertyService.getValue("secretToken");
    }
}
