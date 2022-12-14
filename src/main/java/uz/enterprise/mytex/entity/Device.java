package uz.enterprise.mytex.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.enterprise.mytex.entity.audit.Auditable;

/**
 * @author - 'Zuhriddin Shamsiddionov' at 4:31 PM 10/22/22 on Saturday in October
 */
@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "devices")
public class Device extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "mac_address")
    private String macAddress;

    @Column(name = "device_id", columnDefinition = "varchar(64)")
    private String deviceId;
    @OneToOne(mappedBy = "device", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, optional = false)
    private Session session;
    public void setSession(Session session) {
        if (session == null) {
            if (this.session != null) {
                this.session.setDevice(null);
            }
        } else {
            session.setDevice(this);
        }
        this.session = session;
    }
}
