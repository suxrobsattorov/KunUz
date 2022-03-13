package ok.suxrob.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ok.suxrob.enums.UserRole;

@Getter
@Setter
@AllArgsConstructor
public class ProfileJwtDTO {
    private Integer id;
    private UserRole role;
}
