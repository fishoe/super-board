package your.dream.superboard.article.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class NotFoundArticle extends RuntimeException{
}
