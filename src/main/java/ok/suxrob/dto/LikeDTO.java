package ok.suxrob.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import ok.suxrob.enums.LikeStatus;
import ok.suxrob.enums.LikeType;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LikeDTO {
    private Integer id;
    private LikeStatus status;
    private LocalDateTime createdAd;
    private Integer actionId;
    private LikeType type;
    private Integer profileId;
}
