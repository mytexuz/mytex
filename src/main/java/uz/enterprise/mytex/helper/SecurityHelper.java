package uz.enterprise.mytex.helper;

import java.util.Objects;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import uz.enterprise.mytex.security.CustomUserDetails;

@Component
public class SecurityHelper {


    public static CustomUserDetails getCurrentUser(){
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        return (Objects.isNull(authentication) || isAnonymous(authentication)) ?  null : (CustomUserDetails) authentication.getPrincipal();
    }

    private static boolean isAnonymous(Authentication authentication){
        return authentication.getPrincipal().equals("anonymousUser");
    }
}
