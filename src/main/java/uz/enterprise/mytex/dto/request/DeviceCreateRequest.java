package uz.enterprise.mytex.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.enterprise.mytex.enums.DeviceType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeviceCreateRequest {
    private DeviceType deviceType;
    private String macAddress;
    private String userAgent;
    private String ipAddress;
}
