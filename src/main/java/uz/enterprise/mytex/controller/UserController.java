package uz.enterprise.mytex.controller;

import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.enterprise.mytex.dto.ChangeLangDto;
import uz.enterprise.mytex.dto.ChangeStatusDto;
import uz.enterprise.mytex.dto.RegisterDto;
import uz.enterprise.mytex.dto.UserUpdateDto;
import uz.enterprise.mytex.dto.request.SearchRequest;
import uz.enterprise.mytex.service.UserService;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping(value = "/register", produces = "application/json")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDto registerDTO) {
        return userService.register(registerDTO);
    }

    @PutMapping(value = "/update", produces = "application/json")
    public ResponseEntity<?> update(@Valid @RequestBody UserUpdateDto userDto) {
        return userService.update(userDto);
    }

    @PutMapping(value = "/change-status", produces = "application/json")
    public ResponseEntity<?> changeStatus(@Valid @RequestBody ChangeStatusDto statusDto) {
        return userService.changeStatus(statusDto);
    }

    @GetMapping(value = "/get")
    public ResponseEntity<?> getUser(@RequestParam(value = "id") Long userId) {
        return userService.getUserById(userId);
    }

    @PutMapping(value = "/get-users")
    public ResponseEntity<?> getUsers(@RequestBody SearchRequest search) {
        return userService.getUsers(search);
    }

    @PutMapping(value = "/change-language", produces = "application/json")
    public ResponseEntity<?> changeUserLanguage(@Valid @RequestBody ChangeLangDto langDto) {
        return userService.changeLanguage(langDto);
    }
}
