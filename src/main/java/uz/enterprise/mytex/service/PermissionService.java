package uz.enterprise.mytex.service;

import java.util.List;

import org.springframework.stereotype.Service;
import uz.enterprise.mytex.entity.Permission;
import uz.enterprise.mytex.entity.Role;
import uz.enterprise.mytex.exception.CustomException;
import uz.enterprise.mytex.helper.ResponseHelper;
import uz.enterprise.mytex.repository.PermissionRepository;
import uz.enterprise.mytex.repository.RoleRepository;

@Service
public class PermissionService {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final ResponseHelper responseHelper;

    public PermissionService(RoleRepository roleRepository,
                             PermissionRepository permissionRepository,
                             ResponseHelper responseHelper) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.responseHelper = responseHelper;
    }

    public List<Permission> getPermissionsByUserId(Long userId) {
        Role role = roleRepository.findByUserId(userId).orElseThrow(() -> {
            throw new CustomException(responseHelper.forbidden());
        });
        return permissionRepository.findAllByRoleId(role.getId());
    }
}
