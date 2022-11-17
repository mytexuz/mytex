package uz.enterprise.mytex.dto;


import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.enterprise.mytex.enums.BlockingStatus;
import uz.enterprise.mytex.enums.Period;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BlockedDeviceCreateDto {
    @NotNull(message = "blocked.device.id.not.null")
    private Long deviceId;
    private String reason;
    private Period period;
}
