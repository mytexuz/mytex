package uz.enterprise.mytex.service

import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.password.PasswordEncoder
import uz.enterprise.mytex.BaseSpecification
import uz.enterprise.mytex.dto.*
import uz.enterprise.mytex.dto.request.FilterRequest
import uz.enterprise.mytex.dto.request.SearchRequest
import uz.enterprise.mytex.dto.request.SortRequest
import uz.enterprise.mytex.dto.response.PagedResponse
import uz.enterprise.mytex.dto.response.ResponseData
import uz.enterprise.mytex.dto.response.UserResponse
import uz.enterprise.mytex.entity.Device
import uz.enterprise.mytex.entity.Localization
import uz.enterprise.mytex.entity.Session
import uz.enterprise.mytex.entity.User
import uz.enterprise.mytex.enums.*
import uz.enterprise.mytex.helper.PasswordGeneratorHelper
import uz.enterprise.mytex.helper.ResponseHelper
import uz.enterprise.mytex.repository.DeviceRepository
import uz.enterprise.mytex.repository.SessionRepository
import uz.enterprise.mytex.repository.UserRepository
import uz.enterprise.mytex.repository.specification.SearchSpecification
import uz.enterprise.mytex.security.CustomUserDetails

import java.time.LocalDateTime

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
    private SessionRepository sessionRepository = Mock()
    private DeviceRepository deviceRepository = Mock()
    private Localization localization

    void setup() {
        localization = new Localization(1, "success", Lang.UZ, "Successfully")
        userService = new UserService(
                userRepository,
                passwordEncoder,
                authenticationManager,
                jwtTokenService, responseHelper, deviceRepository, sessionRepository, passwordGeneratorHelper)
    }

    def "Get list of users, search and filter result -> success"() {
        given:
        def filters = [
                new FilterRequest(key: "lastName", operator: Operator.LIKE, propertyType: PropertyType.STRING, value: "yev"),
                new FilterRequest(key: "username", operator: Operator.LIKE, propertyType: PropertyType.STRING, value: "admig")
        ]

        def sorts = [
                new SortRequest(key: "createdAt", direction: SortDirection.DESC)
        ]
        def page = 0, size = 10
        def searchRequest = new SearchRequest(filters: filters, sorts: sorts, page: page, size: size)

        def users = [
                User.builder().id(1).firstName("Admin")
                        .lastName("Adminov").phoneNumber("+998904562122")
                        .status(Status.ACTIVE).username("admin1")
                        .createdAt(LocalDateTime.now()).build(),
                User.builder().id(2).firstName("Shohjahon")
                        .lastName("Rahmataliyev").phoneNumber("+99891457832")
                        .status(Status.ACTIVE).username("awesome")
                        .createdAt(LocalDateTime.now().minusDays(3)).build(),
                User.builder().id(2).firstName("John")
                        .lastName("Doe").phoneNumber("+998911239874")
                        .status(Status.ACTIVE).username("dangerous")
                        .createdAt(LocalDateTime.now().minusDays(7)).build()
        ]

        def pagedResult = new PageImpl<User>(users)

        def content = pagedResult
                .getContent()
                .stream()
                .map(UserService::mapToUserResponse).toList()

        def pagedResponse = new PagedResponse<>(content, pagedResult.getNumber(),
                pagedResult.getSize(), pagedResult.getTotalElements(),
                pagedResult.getTotalPages(), pagedResult.isLast())

        def successResponse = new ResponseEntity([
                data     : pagedResponse,
                message  : localization.message,
                timestamp: getTime()
        ], HttpStatus.OK)

        when:
        def actual = userService.getUsers(searchRequest)

        then:
        1 * userRepository.findAll(_ as SearchSpecification, _ as Pageable) >> pagedResult
        1 * responseHelper.success(_ as Object) >> successResponse
        def actualResponseMap = actual.body as Map<String, Object>
        def actualData = actualResponseMap.get("data") as PagedResponse<UserResponse>
        def actualBody = actualData.content as List<UserResponse>

        actual.statusCode == HttpStatus.OK
        actual.body != null
        actualData.content.size() == content.size()
        actualData.last == pagedResult.last
        actualData.pageNumber == page
        actualData.pageSize == pagedResult.size
        actualData.totalElements == pagedResult.totalElements
        actualBody.size() == users.size()
    }

    def "User service login -> success"() {
        given:
        def loginDto = random.nextObject(LoginDto)

        def session = random.nextObject(Session)

        def device = random.nextObject(Device)

        def auth = new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword())

        def user = random.nextObject(User)

        def userDetails = new CustomUserDetails(null, user.id,
                user.username, user.username + " " + user.lastName, user.getPhoneNumber(), user.getEmail(),
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
        1 * responseHelper.success(_ as Object) >> successResponse
        1 * authentication.principal >> userDetails
        1 * deviceRepository.existsDeviceByUserId(user.id) >> true
        1 * deviceRepository.findByUserId(user.id) >> Optional.of(device)
        1 * sessionRepository.existsSessionByUserId(user.id) >> true
        1 * sessionRepository.findSessionByUserId(user.id) >> Optional.of(session)
        1 * userRepository.findById(user.id) >> Optional.of(user)
        1 * sessionRepository.save(session) >> session

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
        def response = userService.update(updateDto)

        then:
        1 * userRepository.findById(_ as Long) >> Optional.ofNullable(null)
        1 * responseHelper.userDoesNotExist() >> userDoesNotExists

        def actual = response as ResponseEntity<ResponseData<String>>
        actual == userDoesNotExists
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
        def response = userService.changeStatus(statusDto)

        then:
        1 * userRepository.findById(_ as Long) >> Optional.ofNullable(null)
        1 * responseHelper.userDoesNotExist() >> userDoesNotExists

        def actual = response as ResponseEntity<ResponseData<String>>

        actual == userDoesNotExists

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
        def response = userService.getUserById(userId)

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
        def response = userService.getUserById(userId)

        then:
        1 * userRepository.findById(userId) >> Optional.ofNullable(null)
        1 * responseHelper.userDoesNotExist() >> userDoesNotExists
        def actual = response as ResponseEntity<ResponseData<String>>

        actual == userDoesNotExists
    }

}
