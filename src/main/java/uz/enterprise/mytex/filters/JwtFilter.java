package uz.enterprise.mytex.filters;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.enterprise.mytex.security.CustomUserDetails;
import uz.enterprise.mytex.service.CustomUserDetailService;
import uz.enterprise.mytex.service.JwtTokenService;
import uz.enterprise.mytex.util.JwtUtils;

/**
 * @author - 'Zuhriddin Shamsiddionov' at 1:43 PM 10/26/22 on Wednesday in October
 */
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final CustomUserDetailService customUserDetailService;
    private final static List<String> WHITE_LIST = List.of(
            "/api/v1/auth/login",
            "/api/v1/auth/register",
            "/swagger-ui.*",
            "/v3/api-docs.*"
    );
    private final static Function<String, Boolean> isOpenUrl =
            (url) -> WHITE_LIST.stream().anyMatch(url::matches);

    private String parseJwt(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        String prefix = "Bearer ";
        if (StringUtils.hasText(header) && header.startsWith(prefix)) {
            return header.substring(prefix.length());
        }
        return null;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        if (!isOpenUrl.apply(requestURI)) {
            try {
                String token = parseJwt(request);
                final JwtTokenService jwtTokenService = new JwtTokenService(new JwtUtils());
                if (jwtTokenService.isValid(token)) {
                    String username = jwtTokenService.subject(token);
                    CustomUserDetails userDetails = customUserDetailService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        filterChain.doFilter(request, response);
    }
}
