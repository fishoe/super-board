package your.dream.superboard.users.user.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DuplicatedUsername extends RuntimeException{
    private final String username;
}
