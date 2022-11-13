package uz.enterprise.mytex.service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.enterprise.mytex.entity.Permission;
import uz.enterprise.mytex.entity.User;
import uz.enterprise.mytex.helper.ResponseHelper;
import uz.enterprise.mytex.repository.UserRepository;
import uz.enterprise.mytex.security.CustomUserDetails;
import uz.enterprise.mytex.security.PermissionDto;

@Service
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PermissionService permissionService;
    private final ResponseHelper responseHelper;

    public CustomUserDetailService(UserRepository userRepository,
                                   PermissionService permissionService,
                                   ResponseHelper responseHelper) {
        this.userRepository = userRepository;
        this.permissionService = permissionService;
        this.responseHelper = responseHelper;
    }

    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameOrEmail(username).
                orElseThrow(() -> new UsernameNotFoundException(Objects.requireNonNull(
                        responseHelper.userDoesNotExist().getBody()).getMessage()));
        Set<GrantedAuthority> authorities = new HashSet<>();
        if (Objects.nonNull(user.getUserRole())) {
            List<Permission> permissionList = permissionService.getPermissionsByUserId(user.getId());
            for (Permission permission : permissionList) {
                authorities.add(new PermissionDto(permission.getName(), permission.getPath()));
            }
        }
        return CustomUserDetails.builder()
                .id(user.getId())
                .username(user.getUsername())
                .fullName(user.getFirstName() + " " + user.getLastName())
                .password(user.getPassword())
                .authorities(authorities)
                .isEnabled(true)
                .status(user.getStatus())
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .isAccountNonExpired(true)
                .build();
    }
}
