package your.dream.superboard.article.data;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "article_tag", uniqueConstraints = {
        @UniqueConstraint(name = "uc_tag_article_tag", columnNames = {"tag", "article_id"})
})
public class ArticleTag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    @Column(name = "tag", length = 40)
    private String tag;
}