package uz.enterprise.mytex.entity;

import lombok.*;
import uz.enterprise.mytex.entity.audit.Auditable;

import javax.persistence.*;

/**
 * @author - 'Zuhriddin Shamsiddionov' at 10:33 AM 10/24/22 on Monday in October
 */
@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "groups")
public class Group extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "name")
    private String name;

    @Column(nullable = false, name = "description")
    private String description;

}
