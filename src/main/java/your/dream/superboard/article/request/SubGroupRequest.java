package your.dream.superboard.article.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class SubGroupRequest {
    private String code;
    private String name;
}
