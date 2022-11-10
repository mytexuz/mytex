package uz.enterprise.mytex.service;

import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.enterprise.mytex.dto.ChangeStatusDto;
import uz.enterprise.mytex.dto.LoginDto;
import uz.enterprise.mytex.dto.RegisterDto;
import uz.enterprise.mytex.dto.TokenResponseDto;
import uz.enterprise.mytex.dto.UserDto;
import uz.enterprise.mytex.dto.UserUpdateDto;
import uz.enterprise.mytex.entity.User;
import uz.enterprise.mytex.enums.Status;
import uz.enterprise.mytex.exception.CustomException;
import uz.enterprise.mytex.helper.PasswordGeneratorHelper;
import uz.enterprise.mytex.helper.ResponseHelper;
import uz.enterprise.mytex.repository.UserRepository;
import uz.enterprise.mytex.security.CustomUserDetails;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;
    private final ResponseHelper responseHelper;
    private final PasswordGeneratorHelper passwordGeneratorHelper;


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
        return responseHelper.successWithObject(new TokenResponseDto(token, userDetails.getFullName()));
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
        return responseHelper.successWithObject(userDto);
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
            user.setPhoto(user.getPhoto());
        }
        User save = userRepository.save(user);
        UserDto dto = new UserDto(save);
        dto.setPassword(pass);
        return responseHelper.successWithObject(dto);
    }

    public ResponseEntity<?> changeStatus(ChangeStatusDto statusDto) {
        User user = userRepository.findById(statusDto.getId()).orElseThrow(() -> {
            throw new CustomException(responseHelper.userDoesNotExist());
        });
        user.setStatus(statusDto.getStatus());
        userRepository.save(user);
        return responseHelper.successWithObject(Map.of("userId", user.getId(), "status", statusDto.getStatus()));
    }

    public ResponseEntity<?> getUsers() {
        return null;
    }

    public ResponseEntity<?> getUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> {
            throw new CustomException(responseHelper.userDoesNotExist());
        });
        return responseHelper.successWithObject(new UserDto(user));
    }
}
