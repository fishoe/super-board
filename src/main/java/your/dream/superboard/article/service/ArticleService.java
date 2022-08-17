package your.dream.superboard.article.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import your.dream.superboard.article.data.Article;
import your.dream.superboard.article.data.ArticleGroupMajor;
import your.dream.superboard.article.data.ArticleTag;
import your.dream.superboard.article.repository.*;
import your.dream.superboard.article.request.PostArticleRequest;
import your.dream.superboard.article.response.ArticleResponse;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final ArticleGroupMajorRepository groupMajorRepository;
    private final ArticleGroupMinorRepository groupMinorRepository;
    private final ArticleViewLogRepository viewLogRepository;
    private final ArticleModifiedLogRepository modifiedLogRepository;
    private final ArticleTagRepository tagRepository;
    private final ArticleVoteRepository voteRepository;

    @Transactional
    public ArticleResponse postArticle(
            PostArticleRequest postArticleRequest,
            String majorPath,
            String minorCode,
            Authentication authentication
    ){
        var major = groupMajorRepository.findByPath(majorPath).orElseThrow();
        var article = new Article(postArticleRequest, major);
        if (minorCode != null) {
            var minor = groupMinorRepository.findByMajorAndCode(major, minorCode).orElseThrow();
            article.setGroupMinor(minor);
        }
        createTags(article, postArticleRequest.getTags());
        articleRepository.save(article);
        return new ArticleResponse();
    }

    private Set<ArticleTag> createTags(Article article, String raw_tags){
        var tags = raw_tags.split(" ");
        for(var tag : tags){
            article.getTags().add(new ArticleTag(tag));
        }
        return article.getTags();
    }

    private Set<ArticleTag> editingTags(Article article, String raw_tags){
        var tags = Set.of(raw_tags.split(" "));
        Set<String> keep_tags = article.getTags().stream()
                .map(ArticleTag::getTag)
                .filter(tags::contains)
                .collect(Collectors.toSet());

        Set<ArticleTag> input_tags = tags.stream()
                .filter(tag -> !keep_tags.contains(tag))
                .map(ArticleTag::new)
                .collect(Collectors.toSet());

        Set<ArticleTag> delete_tags = article.getTags().stream()
                .filter(tag -> !keep_tags.contains(tag.getTag()))
                .collect(Collectors.toSet());

        article.getTags().removeAll(delete_tags);
        article.getTags().addAll(input_tags);

        return article.getTags();
    }
}
