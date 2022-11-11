package uz.enterprise.mytex.service


import uz.enterprise.mytex.BaseSpecification
import uz.enterprise.mytex.entity.Permission
import uz.enterprise.mytex.entity.Role
import uz.enterprise.mytex.helper.ResponseHelper
import uz.enterprise.mytex.repository.PermissionRepository
import uz.enterprise.mytex.repository.RoleRepository

class PermissionServiceSpec extends BaseSpecification {
    private PermissionService permissionService
    private RoleService roleService
    private PermissionRepository permissionRepository = Mock()
    private RoleRepository roleRepository = Mock()
    private ResponseHelper helper = Mock()

    void setup() {
        roleService = new RoleService(roleRepository)
        permissionService = new PermissionService(roleService, permissionRepository, helper)
    }

    def "get permissions by userId -> success"() {
        given:
        def role = random.nextObject(Role)
        def permission = random.nextObject(Permission)
        def permission2 = random.nextObject(Permission)
        def list = List.of(permission, permission2)
        when:
        def actual = permissionService.getPermissionsByUserId(1)
        then:
        1 * roleRepository.findByUserId(1) >> Optional.of(role)
        1 * permissionRepository.findAllByRoleId(role.id) >> List.of(permission, permission2)
        actual == list
    }
}
