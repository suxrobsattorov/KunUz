package ok.suxrob.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AttachDTO {
    private Integer id;
    private String key;
    private String originName;
    private Long size;
    private String filePath;
    private String extension;
    private LocalDateTime createdAt;
    private String url;
}
