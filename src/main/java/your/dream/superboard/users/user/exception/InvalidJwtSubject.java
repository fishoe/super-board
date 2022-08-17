package your.dream.superboard.users.user.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class InvalidJwtSubject extends RuntimeException{
}
