package uz.enterprise.mytex.security;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.enterprise.mytex.entity.User;
import uz.enterprise.mytex.enums.Lang;
import uz.enterprise.mytex.enums.Status;

@Setter
@Builder
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {
    private Set<GrantedAuthority> authorities;
    private Long id;
    private String username;
    private String fullName;
    private String password;
    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;
    private boolean isEnabled;
    private Lang lang;
    private Status status;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }


    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }

    public Lang getLang() {
        return Objects.isNull(lang) ? Lang.UZ : lang;
    }

    public Status getStatus() {
        return status;
    }

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }
}
