package uz.enterprise.mytex.entity;

import lombok.*;
import uz.enterprise.mytex.entity.audit.Auditable;

import javax.persistence.*;

/**
 * @author - 'Zuhriddin Shamsiddionov' at 4:08 PM 10/24/22 on Monday in October
 */
@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_groups")
public class UserGroup extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "group_id", nullable = false)
    @ManyToOne
    private Group group;

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne
    private User user;
}
