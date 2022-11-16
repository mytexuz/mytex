package uz.enterprise.mytex.security.filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.enterprise.mytex.constant.AppConstants;
import uz.enterprise.mytex.helper.ResponseHelper;
import uz.enterprise.mytex.security.CustomUserDetails;
import uz.enterprise.mytex.service.CustomUserDetailService;
import uz.enterprise.mytex.service.FraudService;
import uz.enterprise.mytex.service.JwtTokenService;
import uz.enterprise.mytex.service.PropertyService;
import uz.enterprise.mytex.service.SessionService;

@Component
@RequiredArgsConstructor
public class AuthorizationFilter extends OncePerRequestFilter {
    private final CustomUserDetailService customUserDetailService;
    private final JwtTokenService jwtTokenService;
    private final PropertyService propertyService;
    private final SessionService sessionService;
    private final ResponseHelper responseHelper;
    private final FraudService fraudService;

    private List<String> getWhiteList() {
        String value = propertyService.getValue("WHITE_LIST");
        if (!value.isBlank()) {
            String[] split = value.split(",");
            return List.of(split);
        }
        return null;
    }


    private boolean isOpenPath(String currentPath) {
        List<String> whiteList = getWhiteList();
        if (currentPath.startsWith("/api/v")) {
            currentPath = currentPath.substring(7);
        }
        if (whiteList != null) {
            for (String s : whiteList) {
                if (currentPath.contains(s)) {
                    return true;
                }
            }
        }
        return false;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String requestUri = request.getRequestURI();
        if (!isOpenPath(requestUri)) {
            try {
                String token = request.getHeader(AppConstants.TOKEN);
                if (jwtTokenService.isValid(token)) {
                    String username = jwtTokenService.subject(token);
                    CustomUserDetails userDetails = customUserDetailService.loadUserByUsername(username);
                    authenticate(request, userDetails);
                    logger.info("User authenticated by username - %s".formatted(username));
                }
            } catch (Exception e) {
                logger.error("internal error", e);
            }
        }
        filterChain.doFilter(request, response);
    }

    private void authenticate(HttpServletRequest request, CustomUserDetails userDetails) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}
