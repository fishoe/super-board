package your.dream.superboard.users.user.response;

import lombok.Getter;
import org.springframework.validation.FieldError;
import your.dream.superboard.users.user.exception.InvalidUserRequest;

import java.util.List;

@Getter
public class InvalidUserRequestResponse {
    private final String message;
    private final List<FieldErrorResponse> fields;

    public InvalidUserRequestResponse(InvalidUserRequest ex){
        message = "Invalid user request";
        fields = ex.getErrors().getFieldErrors().stream()
                .map(FieldErrorResponse::new)
                .toList();
    }

    @Getter
    private static class FieldErrorResponse{
        private final String field;
        private final String message;

        public FieldErrorResponse(FieldError e){
            field = e.getField();
            message = e.getDefaultMessage();
        }
    }
}
