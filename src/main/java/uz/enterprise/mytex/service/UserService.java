package uz.enterprise.mytex.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.enterprise.mytex.common.Generated;
import uz.enterprise.mytex.dto.ChangeLangDto;
import uz.enterprise.mytex.dto.ChangeStatusDto;
import uz.enterprise.mytex.dto.DeviceCreateDto;
import uz.enterprise.mytex.dto.LoginDto;
import uz.enterprise.mytex.dto.RegisterDto;
import uz.enterprise.mytex.dto.TokenResponseDto;
import uz.enterprise.mytex.dto.UserDto;
import uz.enterprise.mytex.dto.UserUpdateDto;
import uz.enterprise.mytex.dto.request.SearchRequest;
import uz.enterprise.mytex.dto.response.PagedResponse;
import uz.enterprise.mytex.dto.response.UserResponse;
import uz.enterprise.mytex.entity.Device;
import uz.enterprise.mytex.entity.Session;
import uz.enterprise.mytex.entity.User;
import uz.enterprise.mytex.enums.Status;
import uz.enterprise.mytex.helper.PasswordGeneratorHelper;
import uz.enterprise.mytex.helper.ResponseHelper;
import uz.enterprise.mytex.repository.DeviceRepository;
import uz.enterprise.mytex.repository.SessionRepository;
import uz.enterprise.mytex.repository.UserRepository;
import uz.enterprise.mytex.repository.specification.SearchSpecification;
import uz.enterprise.mytex.security.CustomUserDetails;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;
    private final ResponseHelper responseHelper;
    private final DeviceRepository deviceRepository;
    private final SessionRepository sessionRepository;
    private final PasswordGeneratorHelper passwordGeneratorHelper;

    @Generated
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtTokenService jwtTokenService, ResponseHelper responseHelper, DeviceRepository deviceRepository, SessionRepository sessionRepository, PasswordGeneratorHelper passwordGeneratorHelper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
        this.responseHelper = responseHelper;
        this.deviceRepository = deviceRepository;
        this.sessionRepository = sessionRepository;
        this.passwordGeneratorHelper = passwordGeneratorHelper;
    }

    @Transactional
    public ResponseEntity<?> login(LoginDto loginDto) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginDto.getUsernameOrEmail(),
                        loginDto.getPassword()));
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long userId = userDetails.getId();
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return responseHelper.userDoesNotExist();
        }
        User user = userOptional.get();
        if (!deviceRepository.existsDeviceByUserId(userId)) {
            DeviceCreateDto deviceDto = loginDto.getDevice();
            Device device = Device.builder()
                    .deviceId(UUID.randomUUID().toString())
                    .deviceType(deviceDto.getDeviceType())
                    .macAddress(deviceDto.getMacAddress())
                    .ipAddress(deviceDto.getIpAddress())
                    .userAgent(deviceDto.getUserAgent())
                    .user(user)
                    .build();
            deviceRepository.save(device);
        }
        Optional<Device> deviceOptional = deviceRepository.findByUserId(userId);
        if (deviceOptional.isEmpty()) {
            return responseHelper.noDataFound();
        }
        Device device = deviceOptional.get();
        if (!sessionRepository.existsSessionByUserId(userId)) {
            String token = jwtTokenService.generateToken(userDetails.getUsername());
            Session session = Session.builder()
                    .user(user)
                    .device(device)
                    .token(token)
                    .status(Status.ACTIVE)
                    .build();
            sessionRepository.save(session);
        }

        Optional<Session> sessionOptional = sessionRepository.findSessionByUserId(userId);
        if (sessionOptional.isEmpty()) {
            return responseHelper.noDataFound();
        }
        Session session = sessionOptional.get();
        session.setStatus(Status.ACTIVE);
        Session save = sessionRepository.save(session);
        return responseHelper.success(new TokenResponseDto(save.getToken(), userDetails.getFullName()));
    }

    public ResponseEntity<?> register(RegisterDto registerDto) {
        Optional<User> optionalUser = userRepository.findByUsernameOrEmail(registerDto.getUsername(), registerDto.getEmail());
        if (optionalUser.isPresent()) {
            return responseHelper.usernameExists();
        }
        String rawPassword = passwordGeneratorHelper.generatePassword();
        User user = User.builder()
                .firstName(registerDto.getFirstName())
                .lastName(registerDto.getLastName())
                .username(registerDto.getUsername())
                .email(registerDto.getEmail())
                .phoneNumber(registerDto.getPhoneNumber())
                .password(passwordEncoder.encode(rawPassword))
                .photo(registerDto.getPhoto())
                .status(Status.PENDING)
                .lang(registerDto.getLang())
                .build();
        User save = userRepository.save(user);
        UserDto userDto = new UserDto(save);
        userDto.setPassword(rawPassword);
        return responseHelper.success(userDto);
    }

    public ResponseEntity<?> update(UserUpdateDto userDto) {
        Optional<User> userOptional = userRepository.findById(userDto.getId());
        if (userOptional.isEmpty()) {
            return responseHelper.userDoesNotExist();
        }
        User user = userOptional.get();
        String pass = passwordGeneratorHelper.generatePassword();
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(pass));
        user.setUsername(userDto.getUsername());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setLang(userDto.getLang());
        if (!userDto.getPhoto().isBlank()) {
            user.setPhoto(userDto.getPhoto());
        }
        User save = userRepository.save(user);
        UserDto dto = new UserDto(save);
        dto.setPassword(pass);
        return responseHelper.success(dto);
    }

    public ResponseEntity<?> changeStatus(ChangeStatusDto statusDto) {
        Optional<User> userOptional = userRepository.findById(statusDto.getId());
        if (userOptional.isEmpty()) {
            return responseHelper.userDoesNotExist();
        }
        User user = userOptional.get();
        user.setStatus(statusDto.getStatus());
        userRepository.save(user);
        return responseHelper.success(Map.of("userId", user.getId(), "status", statusDto.getStatus()));
    }

    public ResponseEntity<?> getUsers(SearchRequest request) {
        SearchSpecification<User> specification = new SearchSpecification<>(request);
        Pageable pageable = SearchSpecification.getPageable(request.getPage(), request.getSize());
        Page<User> page = userRepository.findAll(specification, pageable);
        List<UserResponse> users = page.getContent()
                .stream()
                .map(UserService::mapToUserResponse).toList();
        PagedResponse<UserResponse> pagedResponse = new PagedResponse<>(users, page.getNumber(),
                page.getSize(), page.getTotalElements(), page.getTotalPages(), page.isLast());
        return responseHelper.success(pagedResponse);
    }

    public ResponseEntity<?> getUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            return responseHelper.userDoesNotExist();
        }
        return responseHelper.success(new UserDto(userOptional.get()));
    }

    public ResponseEntity<?> changeLanguage(ChangeLangDto langDto) {
        Optional<User> userOptional = userRepository.findById(langDto.getUserId());
        if (userOptional.isEmpty()) {
            return responseHelper.userDoesNotExist();
        }
        User user = userOptional.get();
        user.setLang(langDto.getLang());
        userRepository.save(user);
        return responseHelper.success();
    }

    public ResponseEntity<?> resetPassword(String usernameOrEmail) {
        Optional<User> userOptional = userRepository.findByUsernameOrEmail(usernameOrEmail);
        if (userOptional.isEmpty()) {
            return responseHelper.userDoesNotExist();
        }
        User user = userOptional.get();
        String newPassword = passwordGeneratorHelper.generatePassword();
        user.setPassword(passwordEncoder.encode(newPassword));
        User save = userRepository.save(user);
        UserDto dto = new UserDto(save);
        dto.setPassword(newPassword);
        return responseHelper.success(dto);
    }


    public static UserResponse mapToUserResponse(User user) {
        return UserResponse
                .builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .registeredDate(user.getCreatedAt())
                .username(user.getUsername())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }
}
