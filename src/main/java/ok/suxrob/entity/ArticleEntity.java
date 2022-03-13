package ok.suxrob.entity;

import lombok.Getter;
import lombok.Setter;
import ok.suxrob.enums.ArticleStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "article")
public class ArticleEntity extends BaseEntity {
    @Column
    private String title;
    @Column
    private String content;
    @Enumerated(EnumType.STRING)
    @Column
    private ArticleStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private ProfileEntity profile;
    @Column(name = "published_date")
    private LocalDateTime publishedDate;

    @OneToMany(mappedBy = "article", fetch = FetchType.LAZY)
    private List<CommentEntity> commentEntityList;

}
