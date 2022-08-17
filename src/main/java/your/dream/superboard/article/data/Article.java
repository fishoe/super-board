package your.dream.superboard.article.data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import your.dream.superboard.article.data.domain.ArticleStatus;
import your.dream.superboard.article.request.PostArticleRequest;
import your.dream.superboard.users.user.data.UserPersonal;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "article")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "subject", length = 120)
    private String subject;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private UserPersonal author;

    @Column(name = "author_name", nullable = false, length = 20)
    private String authorName;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "context", nullable = false)
    private String context;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "modified_at", nullable = false)
    private Instant modifiedAt;

    @Enumerated
    @Column(name = "status", nullable = false)
    private ArticleStatus status;

    @OneToMany(mappedBy = "article", orphanRemoval = true)
    @OrderBy
    private List<Reply> replies = new ArrayList<>();

    @OneToMany(mappedBy = "article", orphanRemoval = true)
    private Set<ArticleVote> votes = new LinkedHashSet<>();

    @OneToMany(mappedBy = "article", orphanRemoval = true)
    private Set<ArticleViewLog> viewLogs = new LinkedHashSet<>();

    @ManyToOne(optional = false)
    @JoinColumn(name = "group_major_id", nullable = false)
    private ArticleGroupMajor groupMajor;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "group_minor_id")
    private ArticleGroupMinor groupMinor;

    @OneToMany(mappedBy = "article", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private Set<ArticleTag> tags = new LinkedHashSet<>();

    public Article(PostArticleRequest postArticleRequest, ArticleGroupMajor groupMajor){
        Instant now = Instant.now();

        status = ArticleStatus.PUBLISHED;

        createdAt = now;
        modifiedAt = now;

        author = null;
        authorName = postArticleRequest.getAuthor();

        this.groupMajor = groupMajor;

        subject = postArticleRequest.getSubject();
        context = postArticleRequest.getContext();
    }

    public Article(PostArticleRequest postArticleRequest, ArticleGroupMajor groupMajor, UserPersonal user){
        this(postArticleRequest, groupMajor);
        author = user;
        authorName = user.getName();
    }
}