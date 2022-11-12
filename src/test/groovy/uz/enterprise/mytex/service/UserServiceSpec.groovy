package uz.enterprise.mytex.service

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.password.PasswordEncoder
import uz.enterprise.mytex.BaseSpecification
import uz.enterprise.mytex.dto.*
import uz.enterprise.mytex.dto.response.ResponseData
import uz.enterprise.mytex.entity.Localization
import uz.enterprise.mytex.entity.User
import uz.enterprise.mytex.enums.Lang
import uz.enterprise.mytex.enums.Status
import uz.enterprise.mytex.exception.CustomException
import uz.enterprise.mytex.helper.PasswordGeneratorHelper
import uz.enterprise.mytex.helper.ResponseHelper
import uz.enterprise.mytex.repository.UserRepository
import uz.enterprise.mytex.security.CustomUserDetails

import static uz.enterprise.mytex.util.DateUtil.getTime

class UserServiceSpec extends BaseSpecification {
    private UserService userService
    private UserRepository userRepository = Mock()
    private Authentication authentication = Mock()
    private PasswordEncoder passwordEncoder = Mock()
    private AuthenticationManager authenticationManager = Mock()
    private JwtTokenService jwtTokenService = Mock()
    private ResponseHelper responseHelper = Mock()
    private PasswordGeneratorHelper passwordGeneratorHelper = Mock()
    private Localization localization

    void setup() {
        localization = new Localization(1, "success", Lang.UZ, "Successfully")
        userService = new UserService(
                userRepository,
                passwordEncoder,
                authenticationManager,
                jwtTokenService, responseHelper, passwordGeneratorHelper)
    }

    def "User service login -> success"() {
        given:
        def loginDto = random.nextObject(LoginDto)

        def auth = new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword())

        def user = random.nextObject(User)

        def userDetails = new CustomUserDetails(null, user.id,
                user.username, user.username + " " + user.lastName,
                user.password, true, true,
                true, true, Lang.UZ, Status.ACTIVE)
        def token = random.nextObject(UUID) as String

        def localization = new Localization(1, "success", Lang.UZ, "Successfully")

        def successResponse = new ResponseEntity([
                data     : new TokenResponseDto(token, userDetails.username),
                message  : localization.message,
                timestamp: getTime()
        ], HttpStatus.OK)

        when:
        def response = userService.login(loginDto)

        then:
        1 * authenticationManager.authenticate(auth) >> authentication
        1 * jwtTokenService.generateToken(user.username) >> token
        1 * responseHelper.success(_ as Object) >> successResponse
        1 * authentication.principal >> userDetails

        def actual = response as ResponseEntity<Object>

        actual.statusCode == HttpStatus.OK
        actual.body != null
    }

    def "User service registration -> success"() {
        given:
        def registerDto = random.nextObject(RegisterDto)
        def user = random.nextObject(User)

        def rawPassword = passwordGeneratorHelper.generatePassword()

        def userDto = new UserDto(user)
        userDto.password = rawPassword

        def successResponse = new ResponseEntity([
                data     : userDto,
                message  : localization.message,
                timestamp: getTime()
        ], HttpStatus.OK)

        when:
        def response = userService.register(registerDto)

        then:
        1 * userRepository.findByUsernameOrEmail(_ as String, _ as String) >> Optional.ofNullable(null)
        1 * responseHelper.success(_ as Object) >> successResponse
        1 * userRepository.save(_) >> user

        def actual = response as ResponseEntity<Object>

        actual.body != null
        actual.statusCode == HttpStatus.OK
    }

    def "User service registration -> failed"() {
        given:
        def registerDto = random.nextObject(RegisterDto)

        def user = random.nextObject(User)

        def loc = new Localization(1, "username.exists.in.the.system", Lang.UZ, "User exists in the system")

        def responseData = new ResponseData<>(null, loc.message)
        responseData.setTimestamp(getTime())
        def userExists = new ResponseEntity(responseData, HttpStatus.BAD_REQUEST)

        when:
        def response = userService.register(registerDto)

        then:
        1 * userRepository.findByUsernameOrEmail(_ as String, _ as String) >> Optional.ofNullable(user)
        1 * responseHelper.usernameExists() >> userExists

        def actual = response as ResponseEntity<Object>

        actual.body != null
        actual.statusCode == HttpStatus.BAD_REQUEST
    }

    def "User service update user with #photo -> success"() {
        given:
        def updateDto = random.nextObject(UserUpdateDto)

        updateDto.photo = ""

        def user = random.nextObject(User)

        def rawPassword = passwordGeneratorHelper.generatePassword()

        def userDto = new UserDto(user)
        userDto.password = rawPassword
        def successResponse = new ResponseEntity([
                data     : userDto,
                message  : localization.message,
                timestamp: getTime()
        ], HttpStatus.OK)

        when:
        def response = userService.update(updateDto)

        then:
        1 * userRepository.findById(_ as Long) >> Optional.of(user)
        1 * responseHelper.success(_ as Object) >> successResponse
        1 * userRepository.save(_) >> user

        def actual = response as ResponseEntity<Object>

        actual.body != null
        actual.statusCode == HttpStatus.OK
    }

    def "User service update user without #photo -> success"() {
        given:
        def updateDto = random.nextObject(UserUpdateDto)

        def user = random.nextObject(User)

        def rawPassword = passwordGeneratorHelper.generatePassword()

        def userDto = new UserDto(user)
        userDto.password = rawPassword
        def successResponse = new ResponseEntity([
                data     : userDto,
                message  : localization.message,
                timestamp: getTime()
        ], HttpStatus.OK)

        when:
        def response = userService.update(updateDto)

        then:
        1 * userRepository.findById(_ as Long) >> Optional.of(user)
        1 * responseHelper.success(_ as Object) >> successResponse
        1 * userRepository.save(_) >> user

        def actual = response as ResponseEntity<Object>

        actual.body != null
        actual.statusCode == HttpStatus.OK
    }

    def "User service update user  -> failed"() {
        given:
        def updateDto = random.nextObject(UserUpdateDto)
        def loc = new Localization(1, "user.does.not.exists", Lang.UZ, "User does not exist!")

        def responseData = new ResponseData<>(null, loc.message)
        responseData.setTimestamp(getTime())
        def userDoesNotExists = new ResponseEntity(responseData, HttpStatus.UNAUTHORIZED)

        when:
        userService.update(updateDto)

        then:
        1 * userRepository.findById(_ as Long) >> Optional.ofNullable(null)
        1 * responseHelper.userDoesNotExist() >> userDoesNotExists

        thrown(CustomException)
    }

    def "User service change status by #id -> success"() {
        given:
        def statusDto = random.nextObject(ChangeStatusDto)
        def user = random.nextObject(User)
        def data = [
                userId: user.id,
                status: statusDto.status
        ]
        def successResponse = new ResponseEntity([
                data     : data,
                message  : localization.message,
                timestamp: getTime()
        ], HttpStatus.OK)

        when:
        def response = userService.changeStatus(statusDto)

        then:
        1 * userRepository.findById(_ as Long) >> Optional.ofNullable(user)
        1 * responseHelper.success(_ as Map) >> successResponse

        def actual = response as ResponseEntity<Map<Object, Object>>
        actual.statusCode == HttpStatus.OK
        actual.body != null
    }

    def "User service change status by #id -> failed"() {
        given:
        def statusDto = random.nextObject(ChangeStatusDto)

        def loc = new Localization(1, "user.does.not.exists", Lang.UZ, "User does not exist!")

        def responseData = new ResponseData<>(null, loc.message)
        responseData.setTimestamp(getTime())
        def userDoesNotExists = new ResponseEntity(responseData, HttpStatus.UNAUTHORIZED)

        when:
        userService.changeStatus(statusDto)

        then:
        1 * userRepository.findById(_ as Long) >> Optional.ofNullable(null)
        1 * responseHelper.userDoesNotExist() >> userDoesNotExists

        thrown(CustomException)
    }

    def "User service get user by #id -> success "() {
        given:
        def userId = 1
        def user = random.nextObject(User)

        def userDto = new UserDto(user)

        def successResponse = new ResponseEntity([
                data     : userDto,
                message  : localization.message,
                timestamp: getTime()
        ], HttpStatus.OK)

        when:
        def response = userService.getUser(userId)

        then:
        1 * userRepository.findById(userId) >> Optional.of(user)
        1 * responseHelper.success(_ as Object) >> successResponse

        def actual = response as ResponseEntity<Object>

        actual.body != null
        actual.statusCode == HttpStatus.OK
    }

    def "User service get user by #id -> failed "() {
        given:
        def userId = 1

        def loc = new Localization(1, "user.does.not.exists", Lang.UZ, "User does not exist!")

        def responseData = new ResponseData<>(null, loc.message)
        responseData.setTimestamp(getTime())
        def userDoesNotExists = new ResponseEntity(responseData, HttpStatus.UNAUTHORIZED)

        when:
        userService.getUser(userId)

        then:
        1 * userRepository.findById(userId) >> Optional.ofNullable(null)
        1 * responseHelper.userDoesNotExist() >> userDoesNotExists

        thrown(CustomException)
    }

}
