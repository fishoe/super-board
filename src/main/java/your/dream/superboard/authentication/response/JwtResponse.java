package your.dream.superboard.authentication.response;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@Data
public class JwtResponse {
    private final String access_token;
    private final String refresh_token;
    private final Long expired_in;
    private final Long refresh_expired_in;
    private final String token_type;
}