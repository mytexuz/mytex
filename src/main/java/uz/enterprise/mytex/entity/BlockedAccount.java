package uz.enterprise.mytex.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import static uz.enterprise.mytex.constant.TableNames.TB_BLOCKED_ACCOUNTS;
import uz.enterprise.mytex.entity.audit.Auditable;
import uz.enterprise.mytex.enums.BlockingStatus;
import uz.enterprise.mytex.enums.Period;

@Entity
@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = TB_BLOCKED_ACCOUNTS)
public class BlockedAccount extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blocked_by")
    private User blockedBy;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private BlockingStatus status;

    @Column(name = "period", nullable = false)
    @Enumerated(EnumType.STRING)
    private Period period;

    @Column(name = "reason")
    private String reason;
}
