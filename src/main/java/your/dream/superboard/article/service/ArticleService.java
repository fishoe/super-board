package your.dream.superboard.article.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import your.dream.superboard.article.data.Article;
import your.dream.superboard.article.data.ArticleTag;
import your.dream.superboard.article.exception.ArticlePermissionDenied;
import your.dream.superboard.article.exception.NotFoundArticle;
import your.dream.superboard.article.exception.NotFoundGroup;
import your.dream.superboard.article.repository.*;
import your.dream.superboard.article.request.PostArticleRequest;
import your.dream.superboard.article.response.ArticleResponse;
import your.dream.superboard.authentication.password.encoder.PasswordEncoderStorage;
import your.dream.superboard.users.user.service.UserService;

import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final UserService userService;
    private final GroupService groupService;

    private final ArticleViewLogRepository viewLogRepository;
    private final ArticleModifiedLogRepository modifiedLogRepository;
    private final ArticleTagRepository tagRepository;
    private final ArticleVoteRepository voteRepository;
    private final PasswordEncoderStorage pwEncoder;

    @Transactional
    public ArticleResponse postArticle(
            PostArticleRequest postArticleRequest,
            String mainGroupPath,
            Authentication authentication
    ){
        var mainGroup = groupService.findMainGroup(mainGroupPath);
        if (!groupService.CheckPermissionWrite(mainGroup,authentication))
            throw new ArticlePermissionDenied();

        Article article;
        if (authentication != null) {
            var user = userService.findUser(authentication.getName());
            article = new Article(postArticleRequest, mainGroup, user, pwEncoder);
        } else
            article = new Article(postArticleRequest, mainGroup, pwEncoder);
        if (postArticleRequest.getGroup() != null) {
            var subCode = postArticleRequest.getGroup();
            var subGroup = groupService.findSubGroup(mainGroup, subCode);
            if (!groupService.CheckPermissionWrite(mainGroup,subGroup,authentication))
                throw new ArticlePermissionDenied();
            article.setGroupSub(subGroup);
        }

        createTags(article, postArticleRequest.getTags());
        articleRepository.save(article);
        return new ArticleResponse(article);
    }

    @Transactional
    public ArticleResponse readArticle(
            Long articleId,
            Authentication authentication
    ){
        var article = articleRepository.findById(articleId).orElseThrow();
        if (!groupService.CheckPermissionRead(article.getGroupMain(),authentication))
            throw new ArticlePermissionDenied();
        if (article.getGroupSub() == null )
            return new ArticleResponse(article);
        else if (!groupService.CheckPermissionRead(article.getGroupMain(),article.getGroupSub(),authentication))
            return new ArticleResponse(article);
        else
            throw new ArticlePermissionDenied();
    }

    private void createTags(Article article, String raw_tags){
        var tags = raw_tags.split(" ");
        for(var tag : tags){
            article.getTags().add(new ArticleTag(article,tag));
        }
    }

    private Set<ArticleTag> editingTags(Article article, String raw_tags){
        var tags = Set.of(raw_tags.split(" "));
        Set<String> keep_tags = article.getTags().stream()
                .map(ArticleTag::getTag)
                .filter(tags::contains)
                .collect(Collectors.toSet());

        Set<ArticleTag> input_tags = tags.stream()
                .filter(tag -> !keep_tags.contains(tag))
                .map(tag_name -> new ArticleTag(article,tag_name))
                .collect(Collectors.toSet());

        Set<ArticleTag> delete_tags = article.getTags().stream()
                .filter(tag -> !keep_tags.contains(tag.getTag()))
                .collect(Collectors.toSet());

        article.getTags().removeAll(delete_tags);
        article.getTags().addAll(input_tags);

        return article.getTags();
    }
}
