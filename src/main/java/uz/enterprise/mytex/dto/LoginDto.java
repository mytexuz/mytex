package uz.enterprise.mytex.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginDto {
    @NotBlank(message = "auth.usernameoremail.not.valid")
    private String usernameOrEmail;

    @NotBlank(message = "auth.password.not.valid")
    private String password;
}
