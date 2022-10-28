package uz.enterprise.mytex.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author - 'Zuhriddin Shamsiddionov' at 7:17 PM 10/28/22 on Friday in October
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenResponseDTO {
    private String token;
    private String tokenType;
}
