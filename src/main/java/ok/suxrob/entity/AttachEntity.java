package ok.suxrob.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "attach")
public class AttachEntity extends BaseEntity {
    private String key;
    @Column
    private String originName;
    @Column
    private Long size;
    @Column
    private String filePath;
    @Column
    private String extension;
}
