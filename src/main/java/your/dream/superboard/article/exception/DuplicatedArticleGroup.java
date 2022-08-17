package your.dream.superboard.article.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class DuplicatedArticleGroup extends RuntimeException{
}
