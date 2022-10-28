package uz.enterprise.mytex.controller;

import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.enterprise.mytex.dto.AuthLoginDTO;
import uz.enterprise.mytex.dto.AuthRegisterDTO;
import uz.enterprise.mytex.dto.TokenResponseDTO;
import uz.enterprise.mytex.service.UserService;

/**
 * @author - 'Zuhriddin Shamsiddionov' at 7:27 PM 10/28/22 on Friday in October
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping(value = "/login", produces = "application/json")
    public ResponseEntity<TokenResponseDTO> login(@Valid @RequestBody AuthLoginDTO loginDTO) {
        return ResponseEntity.ok(userService.login(loginDTO));
    }

    @PostMapping(value = "/register", produces = "application/json")
    public ResponseEntity<Long> login(@Valid @RequestBody AuthRegisterDTO registerDTO) {
        return ResponseEntity.ok(userService.register(registerDTO));
    }
}
