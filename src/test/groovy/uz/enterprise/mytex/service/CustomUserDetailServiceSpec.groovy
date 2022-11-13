package uz.enterprise.mytex.service

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import uz.enterprise.mytex.BaseSpecification
import uz.enterprise.mytex.dto.response.ResponseData
import uz.enterprise.mytex.entity.Localization
import uz.enterprise.mytex.entity.Permission
import uz.enterprise.mytex.entity.User
import uz.enterprise.mytex.enums.Lang
import uz.enterprise.mytex.helper.ResponseHelper
import uz.enterprise.mytex.repository.UserRepository

import static uz.enterprise.mytex.util.DateUtil.getTime

class CustomUserDetailServiceSpec extends BaseSpecification {
    private CustomUserDetailService userDetailService
    private PermissionService permissionService = Mock()
    private UserRepository userRepository = Mock()
    private ResponseHelper responseHelper = Mock()

    void setup() {
        userDetailService = new CustomUserDetailService(
                userRepository,
                permissionService,
                responseHelper)
    }

    def "check CustomUserDetailService-loadByUsername() -> success"() {
        given: "initialize data for loading user by username"
        def username = "awesome"
        def user = random.nextObject(User)
        def permission = random.nextObject(Permission)
        def permission2 = random.nextObject(Permission)
        user.setUsername(username)

        when:
        def customUserDetails = userDetailService.loadUserByUsername(username)

        then:
        1 * userRepository.findByUsernameOrEmail(username) >> Optional.of(user)
        1 * permissionService.getPermissionsByUserId(user.getId()) >> List.of(permission, permission2)

        assert customUserDetails instanceof UserDetails
        assert customUserDetails != null
        assert customUserDetails.getUsername() == username
        assert customUserDetails.getStatus() == user.getStatus()
    }

    def "check CustomUserDetailService-loadByUsername() without authorities -> success"() {
        given: "initialize data for loading user by username"
        def username = "awesome"
        def user = random.nextObject(User)
        user.setUserRole(null)
        user.setUsername(username)

        when:
        def customUserDetails = userDetailService.loadUserByUsername(username)

        then:
        1 * userRepository.findByUsernameOrEmail(username) >> Optional.of(user)
        assert customUserDetails instanceof UserDetails
        assert customUserDetails != null
        assert customUserDetails.getUsername() == username
        assert customUserDetails.getStatus() == user.getStatus()
    }

    def "check CustomUserDetailService-loadByUsername() -> failed"() {
        given: "initialize data for loading user by username"
        def username = "awesome"
        def localization = new Localization(1, "user.does.not.exists", Lang.UZ, "User does not exist!")

        def responseData = new ResponseData<>(null, localization.message)
        responseData.setTimestamp(getTime())
        def userDoesNotExists = new ResponseEntity(responseData, HttpStatus.UNAUTHORIZED)
        when:
        userDetailService.loadUserByUsername(username)

        then:
        1 * responseHelper.userDoesNotExist() >> userDoesNotExists
        1 * userRepository.findByUsernameOrEmail(username) >> Optional.ofNullable(null)
        thrown(UsernameNotFoundException)
    }

}