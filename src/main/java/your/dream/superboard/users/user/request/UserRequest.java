package your.dream.superboard.users.user.request;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;
import your.dream.superboard.common.validator.UserValidator;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@JsonAutoDetect
public class UserRequest{
    @NotNull
    @Pattern(regexp = UserValidator.username)
    private String username;
    @NotNull
    @Pattern(regexp = UserValidator.password)
    private String password;
    @NotNull
    @Pattern(regexp = UserValidator.name)
    private String name;
}
