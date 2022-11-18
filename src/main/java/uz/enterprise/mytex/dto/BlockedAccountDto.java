package uz.enterprise.mytex.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.enterprise.mytex.entity.BlockedAccount;
import uz.enterprise.mytex.entity.User;
import uz.enterprise.mytex.enums.BlockingStatus;
import uz.enterprise.mytex.enums.Period;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BlockedAccountDto {
    private Long blockedAccountId;
    private Long userId;
    private String blockedBy;
    private BlockingStatus status;
    private String unblockDateTime;
    private String blockedDateTime;
    private String reason;
    private Period period;


    public BlockedAccountDto(BlockedAccount blockedAccount) {
        this.blockedAccountId = blockedAccount.getId();
        this.userId = blockedAccount.getUser().getId();
        this.status = blockedAccount.getStatus();
        this.blockedBy = getBlockedByName(blockedAccount.getBlockedBy());
        this.unblockDateTime = getFormatTime(blockedAccount.getUpdatedAt());
        this.blockedDateTime = getFormatTime(blockedAccount.getCreatedAt());
        this.reason = blockedAccount.getReason();
        this.period = blockedAccount.getPeriod();
    }

    public String getFormatTime(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
    }

    public String getBlockedByName(User user) {
        return user.getFirstName() + " " + user.getLastName();
    }
}
