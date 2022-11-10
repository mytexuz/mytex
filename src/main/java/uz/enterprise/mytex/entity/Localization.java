package uz.enterprise.mytex.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import static uz.enterprise.mytex.constant.TableNames.TB_LOCALIZATION;
import uz.enterprise.mytex.entity.audit.TimedAuditable;
import uz.enterprise.mytex.enums.Lang;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = TB_LOCALIZATION, uniqueConstraints = {@UniqueConstraint(columnNames = {"key", "lang"})})
public class Localization extends TimedAuditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "key")
    private String key;

    @Column(name = "lang")
    @Enumerated(EnumType.STRING)
    private Lang lang;

    @Column(name = "message")
    private String message;
}
