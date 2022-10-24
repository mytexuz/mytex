package uz.enterprise.mytex.entity;

/**
 * @author - 'Zuhriddin Shamsiddionov' at 4:00 PM 10/22/22 on Saturday in October
 */

import lombok.*;
import uz.enterprise.mytex.entity.audit.Auditable;

import javax.persistence.*;


@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "photo", nullable = false)
    private String photo;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "status", nullable = false)
    private boolean status;
}
