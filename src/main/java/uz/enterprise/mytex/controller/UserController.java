package uz.enterprise.mytex.controller;

import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.enterprise.mytex.dto.ChangeStatusDto;
import uz.enterprise.mytex.dto.RegisterDto;
import uz.enterprise.mytex.dto.UserUpdateDto;
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

    @GetMapping(value = "/get/{userId}")
    public ResponseEntity<?> getUser(@PathVariable(value = "userId") Long id) {
        return userService.getUser(id);
    }

    @GetMapping(value = "/get-users")
    public ResponseEntity<?> getUsers() {
        return userService.getUsers();
    }

}
