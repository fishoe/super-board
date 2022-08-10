package your.dream.superboard.authentication.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

@Getter
@Setter
public class UsernamePasswordRequest {
    private String username;
    private String password;

    @JsonCreator
    public UsernamePasswordRequest(
            @JsonProperty(value = "username", required = true) String username,
            @JsonProperty(value = "password", required = true) String password
    ){
        this.username = username;
        this.password = password;
    }

    public static UsernamePasswordRequest ConvertFromString(String jsonData) throws IOException {
        ObjectMapper om = new ObjectMapper();
        return om.readValue(jsonData, UsernamePasswordRequest.class);
    }
}
