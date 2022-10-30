package uz.enterprise.mytex.service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.enterprise.mytex.entity.Permission;
import uz.enterprise.mytex.entity.Role;
import uz.enterprise.mytex.entity.User;
import uz.enterprise.mytex.exceptions.UserNotFoundException;
import uz.enterprise.mytex.repository.PermissionRepository;
import uz.enterprise.mytex.repository.RoleRepository;
import uz.enterprise.mytex.repository.UserRepository;
import uz.enterprise.mytex.security.CustomUserDetails;

/**
 * @author - 'Zuhriddin Shamsiddionov' at 1:47 PM 10/26/22 on Wednesday in October
 */
@Service
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public CustomUserDetailService(UserRepository userRepository, RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).
                orElseThrow(() -> new UserNotFoundException("User not found by username %s".formatted(username)));
        Set<GrantedAuthority> authorities = new HashSet<>();
        if (Objects.nonNull(user.getUserRole())) {
            Role role = roleRepository.findByUserId(user.getId()).orElse(new Role());
            authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
            List<Permission> permissionList = permissionRepository.findAllByRoleId(role.getId());
            for (Permission permission : permissionList) {
                authorities.add(new SimpleGrantedAuthority(permission.getAuthority()));
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
