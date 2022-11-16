package uz.enterprise.mytex.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import static uz.enterprise.mytex.constant.TableNames.TB_RESOURCE;
import uz.enterprise.mytex.entity.audit.Auditable;

@Entity
@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = TB_RESOURCE)
public class Resource extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "actual_filename")
    private String actualFileName;

    @Column(name = "extension")
    private String extension;

    @Column(name = "type")
    private String type;
}
