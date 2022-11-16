package uz.enterprise.mytex.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.enterprise.mytex.common.Generated;
import uz.enterprise.mytex.dto.ChangeStatusDto;
import uz.enterprise.mytex.dto.LoginDto;
import uz.enterprise.mytex.dto.RegisterDto;
import uz.enterprise.mytex.dto.TokenResponseDto;
import uz.enterprise.mytex.dto.UserDto;
import uz.enterprise.mytex.dto.UserUpdateDto;
import uz.enterprise.mytex.dto.request.SearchRequest;
import uz.enterprise.mytex.dto.response.PagedResponse;
import uz.enterprise.mytex.dto.response.UserResponse;
import uz.enterprise.mytex.entity.User;
import uz.enterprise.mytex.enums.Status;
import uz.enterprise.mytex.exception.CustomException;
import uz.enterprise.mytex.helper.PasswordGeneratorHelper;
import uz.enterprise.mytex.helper.ResponseHelper;
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
    private final PasswordGeneratorHelper passwordGeneratorHelper;

    @Generated
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtTokenService jwtTokenService, ResponseHelper responseHelper, PasswordGeneratorHelper passwordGeneratorHelper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
        this.responseHelper = responseHelper;
        this.passwordGeneratorHelper = passwordGeneratorHelper;
    }

    public ResponseEntity<?> login(LoginDto loginDto) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword()));
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String token = jwtTokenService.generateToken(userDetails.getUsername());
        return responseHelper.success(new TokenResponseDto(token, userDetails.getFullName()));
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
                .build();
        User save = userRepository.save(user);
        UserDto userDto = new UserDto(save);
        userDto.setPassword(rawPassword);
        return responseHelper.success(userDto);
    }

    public ResponseEntity<?> update(UserUpdateDto userDto) {
        User user = userRepository.findById(userDto.getId()).orElseThrow(() -> {
            throw new CustomException(responseHelper.userDoesNotExist());
        });
        String pass = passwordGeneratorHelper.generatePassword();
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(pass));
        user.setUsername(userDto.getUsername());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPhoneNumber(userDto.getPhoneNumber());
        if (!userDto.getPhoto().isBlank()) {
            user.setPhoto(userDto.getPhoto());
        }
        User save = userRepository.save(user);
        UserDto dto = new UserDto(save);
        dto.setPassword(pass);
        return responseHelper.success(dto);
    }

    public ResponseEntity<?> changeStatus(ChangeStatusDto statusDto) {
        User user = userRepository.findById(statusDto.getId()).orElseThrow(() -> {
            throw new CustomException(responseHelper.userDoesNotExist());
        });
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
        User user = userRepository.findById(id).orElseThrow(() -> {
            throw new CustomException(responseHelper.userDoesNotExist());
        });
        return responseHelper.success(new UserDto(user));
    }


    public Optional<User> getAdministrator(){
        return userRepository.findFirstByFirstNameAndLastName("Admin", "Admin");
    }

    public static UserResponse mapToUserResponse(User user){
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
