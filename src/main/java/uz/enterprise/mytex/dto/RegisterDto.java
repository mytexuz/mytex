package uz.enterprise.mytex.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

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
public class RegisterDto {
    @NotBlank(message = "user.firstname.not.blank")
    private String firstName;

    @NotBlank(message = "user.lastname.not.blank")
    private String lastName;

    @Pattern(regexp = "^[+]998([378]{2}|(9[013-57-9]))\\d{7}$", message = "user.phone.not.valid")
    private String phoneNumber;

    private String photo;

    @NotBlank(message = "user.username.not.valid")
    private String username;

    @Email(message = "user.email.not.valid")
    private String email;
}
