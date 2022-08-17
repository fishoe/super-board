package your.dream.superboard.users.user.response;

import lombok.Data;
import lombok.Getter;
import your.dream.superboard.users.user.data.UserPersonal;

@Getter
public class UserResponse {
    private final Long id;
    private String username;
    private final String name;

    public UserResponse(UserPersonal user){
        if (user.getUserAuthentication() != null)
            username = user.getUserAuthentication().getUsername();
        id = user.getId();
        name = user.getName();
    }
}
