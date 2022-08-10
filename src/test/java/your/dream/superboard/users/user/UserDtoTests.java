package your.dream.superboard.users.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import your.dream.superboard.users.user.request.UserRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserDtoTests {
    @Test
    void UserDtoConverts() throws JsonProcessingException {
        String json = "{\"username\":\"test_user\",\"password\":\"password1\",\"name\":\"the man\"}";

        ObjectMapper om = new ObjectMapper();
        UserRequest user = om.readValue(json, UserRequest.class);
        assertEquals(user.getUsername(),"test_user");
        assertEquals(user.getPassword(), "password1");
        assertEquals(user.getName(),"the man");
    }
}
