package ok.suxrob.dto.filter;

import lombok.Data;
import ok.suxrob.enums.ProfileStatus;
import ok.suxrob.enums.UserRole;

import java.time.LocalDate;

@Data
public class ProfileFilterDTO {
    private Integer id;
    private String name;
    private String surname;
    private String email;
    private UserRole role;
    private ProfileStatus status;
    private LocalDate fromDate;
    private LocalDate toDate;
}
