package uz.enterprise.mytex.service

import org.springframework.security.core.userdetails.UserDetails
import uz.enterprise.mytex.BaseSpecification
import uz.enterprise.mytex.entity.Permission
import uz.enterprise.mytex.entity.Role
import uz.enterprise.mytex.entity.User
import uz.enterprise.mytex.helper.ResponseHelper
import uz.enterprise.mytex.repository.LocalizationRepository
import uz.enterprise.mytex.repository.PermissionRepository
import uz.enterprise.mytex.repository.RoleRepository
import uz.enterprise.mytex.repository.UserRepository

class CustomUserDetailServiceSpec extends BaseSpecification {
    private CustomUserDetailService userDetailService
    private PermissionService permissionService
    private LocalizationService localizationService
    private RoleService roleService
    private UserRepository userRepository = Mock()
    private PermissionRepository permissionRepository = Mock()
    private RoleRepository roleRepository = Mock()
    private LocalizationRepository localizationRepository = Mock()
    private ResponseHelper helper

    void setup() {
        roleService = new RoleService(roleRepository)
        localizationService = new LocalizationService(localizationRepository)
        helper = new ResponseHelper(localizationService)
        permissionService = new PermissionService(roleService, permissionRepository, helper)
        userDetailService = new CustomUserDetailService(
                userRepository,
                permissionService,
                helper)
    }

    def "check CustomUserDetailService-loadByUsername() -> success"() {
        given: "initialize data for loading user by username"
        def username = "awesome"
        def user = random.nextObject(User)
        def role = random.nextObject(Role)
        def permission = random.nextObject(Permission)
        def permission2 = random.nextObject(Permission)
        user.setUsername(username)

        when:
        def customUserDetails = userDetailService.loadUserByUsername(username)

        then:
        1 * userRepository.findByUsernameOrEmail(username) >> Optional.of(user)
        1 * roleRepository.findByUserId(user.getId()) >> Optional.of(role)
        1 * permissionRepository.findAllByRoleId(role.getId()) >> List.of(permission, permission2)
        assert customUserDetails instanceof UserDetails
        assert customUserDetails != null
        assert customUserDetails.getUsername() == username
        assert customUserDetails.getStatus() == user.getStatus()
    }
}