package your.dream.superboard.users.user.request;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;

@Getter
@JsonAutoDetect
public class UserRequest{
    private String username;
    private String password;
    private String name;
}
