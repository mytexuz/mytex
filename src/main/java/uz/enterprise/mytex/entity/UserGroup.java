package uz.enterprise.mytex.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.enterprise.mytex.entity.audit.Auditable;

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
