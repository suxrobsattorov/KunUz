package ok.suxrob.dto.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.*;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegistrationDTO {
    @NotEmpty(message = "name can not be empty")
    @NotNull(message = "name can not be null")
    private String name;
    private String surname;
    @NotEmpty(message = "email can not be empty")
    @NotNull(message = "email can not be null")
    @Email(message = "cannot be email")
    private String email;
    @NotEmpty(message = "login can not be empty")
    @NotNull(message = "login can not be null")
    private String login;
    @NotEmpty(message = "password can not be empty")
    @NotNull(message = "password can not be null")
    @Max(value = 10, message = "max 10")
    @Min(value = 5, message = "min 5")
    private String password;
}
