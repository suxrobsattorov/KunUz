package ok.suxrob.entity;

import lombok.Getter;
import lombok.Setter;
import ok.suxrob.enums.LikeStatus;
import ok.suxrob.enums.LikeType;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "liked")
public class LikeEntity extends BaseEntity {
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LikeStatus status;
    @Column(name = "action_id")
    private Integer actionId;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LikeType type;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private ProfileEntity profile;
}
