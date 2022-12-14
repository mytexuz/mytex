package uz.enterprise.mytex.entity.audit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * @author - 'Zuhriddin Shamsiddionov' at 4:00 PM 10/22/22 on Saturday in October
 */
@Getter
@Setter
@JsonIgnoreProperties(
        value = {"createdBy", "lastModifiedBy",
                "createdTime", "updatedTime"},
        allowGetters = true
)
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Auditable {
    @CreatedBy
    @Column(name = "created_by", columnDefinition = "bigint default 1", updatable = false)
    protected Long createdBy;

    @LastModifiedBy
    @Column(name = "last_modified_by", columnDefinition = "bigint default 1")
    protected Long lastModifiedBy;

    @CreationTimestamp
    @Column(name = "created_time")
    private LocalDateTime createdTime;

    @UpdateTimestamp
    @Column(name = "updated_time")
    private LocalDateTime updatedTime;

}
