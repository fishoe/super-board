package your.dream.superboard.users.user.response;

import lombok.Getter;
import your.dream.superboard.users.user.data.UserPersonal;

@Getter
public class UserResponse {
    private String username;
    private String name;

    public UserResponse(UserPersonal user){
        if (user.getUserAuthentication() != null)
            username = user.getUserAuthentication().getUsername();
        name = user.getName();
    }
}
