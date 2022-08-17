package your.dream.superboard.article.response;

import lombok.Getter;
import your.dream.superboard.article.data.Article;

@Getter
public class ArticleResponse {
    private final Long id;
    private final String subject;
    private final String context;

    public ArticleResponse(Article article) {
        id = article.getId();
        subject = article.getSubject();
        context = article.getContext();
    }
}
