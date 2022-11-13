package uz.enterprise.mytex.config;

import java.util.Objects;
import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import uz.enterprise.mytex.security.CustomUserDetails;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class AuditableConfig {

    @Bean(name = "auditorAware")
    public AuditorAware<Long> auditorAware() {
        return new AuditAwareImpl();
    }
}

class AuditAwareImpl implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.isNull(authentication) || !authentication.isAuthenticated()
                || authentication instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        }
        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        return Optional.ofNullable(principal.getId());
    }
}
