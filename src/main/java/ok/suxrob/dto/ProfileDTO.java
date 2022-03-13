package ok.suxrob.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import ok.suxrob.enums.ProfileStatus;
import ok.suxrob.enums.UserRole;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileDTO {
    private Integer id;
    private String name;
    private String surname;
    private String login;
    private String pswd;
    private String email;
    private UserRole userRole;
    private ProfileStatus status;

    private String jwt;//token
}
