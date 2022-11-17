package uz.enterprise.mytex.security.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Objects;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import uz.enterprise.mytex.constant.AppConstants;
import uz.enterprise.mytex.entity.Session;
import uz.enterprise.mytex.enums.Status;
import uz.enterprise.mytex.exception.CustomException;
import uz.enterprise.mytex.helper.ResponseHelper;
import uz.enterprise.mytex.helper.SecurityHelper;
import uz.enterprise.mytex.security.CustomUserDetails;
import uz.enterprise.mytex.security.PermissionDto;
import uz.enterprise.mytex.service.FraudService;
import uz.enterprise.mytex.service.SessionService;

@Component
@RequiredArgsConstructor
public class AuthorizationInterceptor implements HandlerInterceptor {
    private final FraudService fraudService;
    private final SessionService sessionService;
    private final ResponseHelper responseHelper;


    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        var token = request.getHeader(AppConstants.TOKEN);
        var deviceId = request.getHeader(AppConstants.DEVICE_ID);
        var requestUri = request.getRequestURI();

        if (headersExist(token, deviceId)){
            CustomUserDetails user = SecurityHelper.getCurrentUser();
            if (fraudService.isDeviceBlockedByDeviceId(deviceId)) {
                throw new CustomException(responseHelper.deviceBlocked());
            }
            Session session = sessionService.getSessionByUserId(user.getId());
            if (!session.getToken().equals(token)) {
                throw new CustomException(responseHelper.unauthorized());
            }
            if (session.getStatus().equals(Status.DISABLED)) {
                throw new CustomException(responseHelper.sessionDisabled());
            }

            Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
            for (GrantedAuthority authority : authorities) {
                var permittedPath = ((PermissionDto) authority).getPath();
                if (!Objects.equals(requestUri, permittedPath)) {
                    throw new CustomException(responseHelper.forbidden());
                }
            }
        }
        return true;
    }

    private boolean headersExist(String token, String deviceId){
        return Objects.nonNull(token) && Objects.nonNull(deviceId);
    }
}
