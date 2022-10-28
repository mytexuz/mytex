package uz.enterprise.mytex.service;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.enterprise.mytex.entity.RolePermission;
import uz.enterprise.mytex.entity.User;
import uz.enterprise.mytex.exceptions.UserNotFoundException;
import uz.enterprise.mytex.repository.UserRepository;
import uz.enterprise.mytex.security.CustomUserDetails;

/**
 * @author - 'Zuhriddin Shamsiddionov' at 1:47 PM 10/26/22 on Wednesday in October
 */
@Service
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).
                orElseThrow(() -> new UserNotFoundException("User not found by username %s".formatted(username)));
        Set<GrantedAuthority> authorities = new HashSet<>();
        if (Objects.nonNull(user.getUserRole())) {
            authorities.add(new SimpleGrantedAuthority(user.getUserRole().getRole().getAuthority()));
            for (RolePermission rolePermission : user.getUserRole().getRole().getRolePermissions()) {
                authorities.add(new SimpleGrantedAuthority(rolePermission.getPermission().getAuthority()));
            }
        }
        return CustomUserDetails.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(authorities)
                .isEnabled(user.getStatusBoolean())
                .isAccountNonLocked(user.getStatusBoolean())
                .isCredentialsNonExpired(user.getStatusBoolean())
                .isAccountNonExpired(user.getStatusBoolean())
                .build();
    }
}
