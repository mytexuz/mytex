package uz.enterprise.mytex.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.enterprise.mytex.entity.BlockedDevice;
import uz.enterprise.mytex.entity.User;
import uz.enterprise.mytex.enums.BlockingStatus;
import uz.enterprise.mytex.enums.DeviceType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BlockedDeviceDto {
    private Long blockedDeviceId;
    private Long deviceId;
    private String blockedBy;
    private BlockingStatus status;
    private String unblockDateTime;
    private String blockedDateTime;
    private String reason;
    private DeviceType deviceType;
    private String macAddress;
    private String ipAddress;
    private String deviceIdStr;
    private String deviceOwner;
    private String userAgent;

    public BlockedDeviceDto(BlockedDevice blockedDevice) {
        this.blockedDeviceId = blockedDevice.getId();
        this.deviceId = blockedDevice.getDevice().getId();
        this.status = blockedDevice.getStatus();
        this.unblockDateTime = getFormatTime(blockedDevice.getUpdatedAt());
        this.blockedDateTime = getFormatTime(blockedDevice.getCreatedAt());
        this.reason = blockedDevice.getReason();
        this.deviceType = blockedDevice.getDevice().getDeviceType();
        this.macAddress = blockedDevice.getDevice().getMacAddress();
        this.ipAddress = blockedDevice.getDevice().getIpAddress();
        this.deviceIdStr = blockedDevice.getDevice().getDeviceId();
        this.deviceOwner = getOwner(blockedDevice.getDevice().getUser());
        this.userAgent = blockedDevice.getDevice().getUserAgent();
    }

    public String getFormatTime(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
    }

    public String getOwner(User user) {
        return user.getFirstName() + " " + user.getLastName();
    }
}
