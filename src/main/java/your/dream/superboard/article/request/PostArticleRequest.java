package your.dream.superboard.article.request;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PostArticleRequest {
    private String abbr;
    @NotNull
    @Length(max = 100, min = 2)
    private String subject;

    @NotNull
    @Length(max = 10000)
    private String context;

    @Length
    private String tags;

    @Length(max = 20)
    private String author;
}
