package uz.enterprise.mytex.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import uz.enterprise.mytex.entity.audit.Auditable;

import javax.persistence.*;

/**
 * @author - 'Zuhriddin Shamsiddionov' at 4:37 PM 10/22/22 on Saturday in October
 */
@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sessions")
public class Session extends Auditable {
    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(generator = "uuid2")
    private String uniqueId;

    @JoinColumn(nullable = false, name = "device_id")
    @OneToOne(fetch = FetchType.EAGER)
    private Device device;

    @JoinColumn(nullable = false, name = "user_id")
    @OneToOne(fetch = FetchType.EAGER)
    private User user;


    @Column(name = "token", nullable = false)
    private String token;
}
