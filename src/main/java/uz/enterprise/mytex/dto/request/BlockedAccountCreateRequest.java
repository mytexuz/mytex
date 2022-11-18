package uz.enterprise.mytex.dto.request;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.enterprise.mytex.enums.Period;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BlockedAccountCreateRequest {
    @NotNull(message = "user.id.not.null")
    private Long accountId;
    private Period period;
    private String reason;
}
