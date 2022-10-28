package uz.enterprise.mytex.util;

import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Objects;
import java.util.function.Function;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.NonNull;
import org.springframework.stereotype.Component;

/**
 * @author - 'Zuhriddin Shamsiddionov' at 3:18 PM 10/26/22 on Wednesday in October
 */
@Component
public class JwtUtils {
    public String getSubject(String token, String secret) {
        return getClaim(token, Claims::getSubject, secret);
    }
    private <T> T getClaim(String token, Function<Claims, T> function, String secret) {
        Jws<Claims> claimsJws = jwtClaims(token, secret);
        Claims claims = claimsJws.getBody();
        return function.apply(claims);
    }
    public String jwt(@NonNull final String subject,
                      @NonNull final String secret) {
        Instant now = Instant.now(Clock.systemDefaultZone());
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(3650, ChronoUnit.DAYS)))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
    public Jws<Claims> jwtClaims(@NonNull final String token, @NonNull final String secret) {
        try {
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public boolean isTokenValid(String token, String secret) {
        String subject = getSubject(token, secret);
        return Objects.nonNull(subject);
    }
}
