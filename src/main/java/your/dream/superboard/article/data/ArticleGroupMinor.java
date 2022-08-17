package your.dream.superboard.article.data;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "article_group_minor")
public class ArticleGroupMinor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST, optional = false)
    @JoinColumn(name = "major_id", nullable = false)
    private ArticleGroupMajor major;


    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @OneToMany(mappedBy = "groupMinor", orphanRemoval = true)
    private Set<Article> articles = new LinkedHashSet<>();

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "code", nullable = false, length = 40)
    private String code;

}