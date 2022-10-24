package uz.enterprise.mytex.entity;

import lombok.*;
import uz.enterprise.mytex.entity.audit.Auditable;

import javax.persistence.*;

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

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "mac_address", nullable = false)
    private String macAddress;

    @Column(name = "device_id", nullable = false)
    private Long deviceId;
}
