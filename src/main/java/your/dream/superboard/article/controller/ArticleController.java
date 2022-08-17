package your.dream.superboard.article.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import your.dream.superboard.article.request.PostArticleRequest;
import your.dream.superboard.article.response.ArticleResponse;
import your.dream.superboard.article.service.ArticleService;

import java.net.URI;

import static your.dream.superboard.common.Path.API_PREFIX;
import static your.dream.superboard.common.Path.ARTICLE;

@RestController
@RequestMapping(value = API_PREFIX + ARTICLE)
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    @PostMapping(path = "/{mainGroup}")
    public ResponseEntity<ArticleResponse> postArticle(
            @PathVariable String mainGroup,
            @RequestBody PostArticleRequest articleRequest,
            Authentication authentication
    ){
        var article = articleService.postArticle(articleRequest,mainGroup,authentication);
        return ResponseEntity.created(
                URI.create(API_PREFIX + ARTICLE + "/" + article.getId())
        ).body(article);
    }

    @GetMapping(path = "/{articleId}")
    public ResponseEntity<ArticleResponse> readArticle(
            @PathVariable Long articleId,
            Authentication authentication
    ){
        var response = articleService.readArticle(articleId,authentication);
        return ResponseEntity.ok(response);
    }
}
