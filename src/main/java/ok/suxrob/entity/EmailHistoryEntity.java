package ok.suxrob.entity;

import lombok.Getter;
import lombok.Setter;
import ok.suxrob.enums.EmailStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "email_history")
public class EmailHistoryEntity extends BaseEntity {
    @Column
    private String fromAccount;
    @Column
    private String toAccount;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EmailStatus status;
    @Column
    private LocalDateTime usedAt;
}
