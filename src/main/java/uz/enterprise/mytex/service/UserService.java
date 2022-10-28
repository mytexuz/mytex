package uz.enterprise.mytex.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.enterprise.mytex.dto.AuthLoginDTO;
import uz.enterprise.mytex.dto.AuthRegisterDTO;
import uz.enterprise.mytex.dto.TokenResponseDTO;
import uz.enterprise.mytex.entity.User;
import uz.enterprise.mytex.enums.Status;
import uz.enterprise.mytex.repository.UserRepository;
import uz.enterprise.mytex.security.CustomUserDetails;

/**
 * @author - 'Zuhriddin Shamsiddionov' at 7:29 PM 10/28/22 on Friday in October
 */
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;

    public TokenResponseDTO login(AuthLoginDTO loginDTO) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String token = jwtTokenService.generateToken(userDetails);
        return new TokenResponseDTO(token, "Bearer");
    }
    public Long register(AuthRegisterDTO registerDTO) {
        User user = User.builder()
                .firstName(registerDTO.getFirstName())
                .lastName(registerDTO.getLastName())
                .username(registerDTO.getUsername())
                .phoneNumber(registerDTO.getPhoneNumber())
                .password(passwordEncoder.encode(registerDTO.getPassword()))
                .status(Status.ACTIVE)
                .build();
        return userRepository.save(user).getId();
    }
}
