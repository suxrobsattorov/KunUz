package ok.suxrob.dto.filter;

import lombok.Data;
import ok.suxrob.enums.ArticleStatus;

import java.time.LocalDate;

@Data
public class ArticleFilterDTO {
    private String title;
    private Integer profileId;
    private Integer articleId;
    private ArticleStatus status;
    private LocalDate fromDate;
    private LocalDate toDate;
}
