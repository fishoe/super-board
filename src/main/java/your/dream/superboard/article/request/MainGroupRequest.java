package your.dream.superboard.article.request;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;

@Getter
@JsonAutoDetect
public class MainGroupRequest {
    private String path;
    private String name;
    private String type;
}
