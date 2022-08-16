package your.dream.superboard.users.user.response;

import lombok.Getter;
import your.dream.superboard.users.user.exception.DuplicatedUsername;

@Getter
public class DuplicatedUserNameResponse {
    private final String message;
    private final String username;
    public DuplicatedUserNameResponse(DuplicatedUsername ex){
        username = ex.getUsername();
        message = "duplicated username : " + username;
    }
}
