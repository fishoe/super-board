package your.dream.superboard.article.data;

import lombok.Getter;
import lombok.Setter;
import your.dream.superboard.article.data.domain.MajorType;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "article_group_major", indexes = {
        @Index(name = "idx_articlegroupmajor_path", columnList = "path")
})
public class ArticleGroupMajor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "path", nullable = false, unique = true, length = 16)
    private String path;

    @Column(name = "name", nullable = false, unique = true, length = 20)
    private String name;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @OneToMany(mappedBy = "groupMajor", cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, orphanRemoval = true)
    @OrderBy("id DESC")
    private List<Article> articles = new ArrayList<>();

    @OneToMany(mappedBy = "major", cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, orphanRemoval = true)
    @OrderBy("id DESC")
    private List<ArticleGroupMinor> minors = new ArrayList<>();

    @Enumerated
    @Column(name = "major_type", nullable = false)
    private MajorType majorType;
}