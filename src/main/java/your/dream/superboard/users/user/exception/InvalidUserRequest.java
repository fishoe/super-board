package your.dream.superboard.users.user.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.Errors;

@RequiredArgsConstructor
@Getter
public class InvalidUserRequest extends RuntimeException{
    private final Errors errors;
}
