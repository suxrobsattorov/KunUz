package ok.suxrob.entity;

import lombok.Getter;
import lombok.Setter;
import ok.suxrob.enums.ProfileStatus;
import ok.suxrob.enums.UserRole;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


@Setter
@Getter
@Entity
@Table(name = "profile")
public class ProfileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String name;
    @Column
    private String surname;
    @Column(unique = true, nullable = false)
    private String login;
    @Column(nullable = false)
    private String pswd;
    @Column(unique = true)
    private String email;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProfileStatus status;

    @Column(name = "last_active_date")
    private LocalDateTime lastActiveDate;

    @OneToMany(mappedBy = "profile", fetch = FetchType.LAZY)
    private List<CommentEntity> commentEntityList;
    @OneToMany(mappedBy = "profile", fetch = FetchType.LAZY)
    private List<LikeEntity> likeEntityList;
}
