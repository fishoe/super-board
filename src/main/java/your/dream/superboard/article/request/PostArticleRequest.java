package your.dream.superboard.article.request;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Data
@JsonAutoDetect
public class PostArticleRequest {
    private String group;
    @NotNull
    @Length(max = 100, min = 2)
    private String subject;

    @NotNull
    @Length(max = 10000)
    private String context;

    @Length(max = 1000)
    private String tags;

    @Length(max = 20)
    private String author;

    @Length(min = 4, max = 32)
    private String password;

}
