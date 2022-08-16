package your.dream.superboard.users.user.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import your.dream.superboard.users.user.exception.DuplicatedUsername;
import your.dream.superboard.users.user.exception.InvalidUserRequest;
import your.dream.superboard.users.user.response.DuplicatedUserNameResponse;
import your.dream.superboard.users.user.response.InvalidUserRequestResponse;

@RestControllerAdvice
public class UserServiceExceptionHandler {
    @ExceptionHandler(InvalidUserRequest.class)
    public ResponseEntity<?> invalidUserRequest(InvalidUserRequest ex){
        return ResponseEntity.badRequest().body(new InvalidUserRequestResponse(ex));
    }

    @ExceptionHandler(DuplicatedUsername.class)
    public ResponseEntity<?> duplicatedUsername(DuplicatedUsername ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new DuplicatedUserNameResponse(ex));
    }
}
