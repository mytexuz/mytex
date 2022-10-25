package uz.enterprise.mytex.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

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
public class Session {
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
