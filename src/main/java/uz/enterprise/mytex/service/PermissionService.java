package uz.enterprise.mytex.service;

import java.util.List;

import org.springframework.stereotype.Service;
import uz.enterprise.mytex.entity.Permission;
import uz.enterprise.mytex.entity.Role;
import uz.enterprise.mytex.helper.ResponseHelper;
import uz.enterprise.mytex.repository.PermissionRepository;

@Service
public class PermissionService {
    private final RoleService roleService;
    private final PermissionRepository permissionRepository;
    private final ResponseHelper responseHelper;

    public PermissionService(RoleService roleService,
                             PermissionRepository permissionRepository,
                             ResponseHelper responseHelper) {
        this.roleService = roleService;
        this.permissionRepository = permissionRepository;
        this.responseHelper = responseHelper;
    }

    public List<Permission> getPermissionsByUserId(Long userId) {
        Role role = roleService.getRoleByUser(userId);
        return permissionRepository.findAllByRoleId(role.getId());
    }
}
