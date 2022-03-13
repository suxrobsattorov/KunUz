package ok.suxrob.dto.filter;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CommentFilterDTO {
    private Integer id;
    private Integer profileId;
    private Integer articleId;
    private LocalDate fromDate;
    private LocalDate toDate;
}
