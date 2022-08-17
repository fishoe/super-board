package your.dream.superboard.article.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import your.dream.superboard.article.data.ArticleGroupMajor;

import java.util.Optional;

public interface ArticleGroupMajorRepository extends JpaRepository<ArticleGroupMajor, Long> {
    Optional<ArticleGroupMajor> findByPath(String path);

}