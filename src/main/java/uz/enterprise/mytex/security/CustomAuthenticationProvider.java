package uz.enterprise.mytex.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import uz.enterprise.mytex.enums.Status;
import static uz.enterprise.mytex.enums.Status.isDisabled;
import static uz.enterprise.mytex.enums.Status.isPending;
import uz.enterprise.mytex.exception.CustomAuthenticationException;
import uz.enterprise.mytex.helper.ResponseHelper;


public class CustomAuthenticationProvider extends DaoAuthenticationProvider {
    @Autowired
    private ResponseHelper responseHelper;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        super.additionalAuthenticationChecks(userDetails, authentication);
        CustomUserDetails customUserDetail = (CustomUserDetails) userDetails;
        Status status = customUserDetail.getStatus();
        if (isPending(status)) {
            throw new CustomAuthenticationException(responseHelper.accountPending());
        }
        if (isDisabled(status)) {
            throw new CustomAuthenticationException(responseHelper.accountBlocked());
        }
    }
}
