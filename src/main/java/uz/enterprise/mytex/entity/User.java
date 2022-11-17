package uz.enterprise.mytex.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import static uz.enterprise.mytex.constant.TableNames.TB_USER;
import uz.enterprise.mytex.entity.audit.Auditable;
import uz.enterprise.mytex.enums.Lang;
import uz.enterprise.mytex.enums.Status;

@Entity
@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = TB_USER)
@EntityListeners(AuditingEntityListener.class)
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

    @Column(name = "photo")
    private String photo;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "lang")
    @Enumerated(EnumType.STRING)
    private Lang lang;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, optional = false)
    private Session session;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, optional = false)
    private UserRole userRole;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, optional = false)
    private Device device;

    public void setSession(Session session) {
        if (session == null) {
            if (this.session != null) {
                this.session.setUser(null);
            }
        } else {
            session.setUser(this);
        }
        this.session = session;
    }

    public void setUserRole(UserRole userRole) {
        if (userRole == null) {
            if (this.userRole != null) {
                this.userRole.setUser(null);
            }
        } else {
            userRole.setUser(this);
        }
        this.userRole = userRole;
    }

    public void setDevice(Device device) {
        if (device == null) {
            if (this.device != null) {
                this.device.setUser(null);
            }
        } else {
            device.setUser(this);
        }
        this.device = device;
    }
}
