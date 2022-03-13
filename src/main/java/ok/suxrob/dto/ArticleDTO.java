package ok.suxrob.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import ok.suxrob.enums.ArticleStatus;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleDTO {
    private Integer id;

    private String title;
    private String content;

    private Integer profileId;
    private ArticleStatus status;

    private LocalDateTime createdDate;
    private LocalDateTime publishedDate;

}
