package your.dream.superboard.article.data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import your.dream.superboard.article.data.domain.ArticleStatus;
import your.dream.superboard.article.request.PostArticleRequest;
import your.dream.superboard.authentication.password.encoder.PasswordEncoderStorage;
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
    @JoinColumn(name = "group_main_id", nullable = false)
    private ArticleGroupMain groupMain;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "group_sub_id")
    private ArticleGroupSub groupSub;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ArticleTag> tags = new LinkedHashSet<>();

    @Column(name = "password", length = 200)
    private String password;

    public Article(
            PostArticleRequest postArticleRequest,
            ArticleGroupMain groupMain,
            PasswordEncoderStorage pwEncoder
    ){
        Instant now = Instant.now();

        status = ArticleStatus.PUBLISHED;

        createdAt = now;
        modifiedAt = now;

        author = null;
        authorName = postArticleRequest.getAuthor();
        if (postArticleRequest.getPassword() != null)
            password = pwEncoder.getPasswordEncoder().encode(postArticleRequest.getPassword());

        this.groupMain = groupMain;

        subject = postArticleRequest.getSubject();
        context = postArticleRequest.getContext();
    }

    public Article(
            PostArticleRequest postArticleRequest,
            ArticleGroupMain groupMain,
            UserPersonal user,
            PasswordEncoderStorage pwEncoder
    ){
        this(postArticleRequest, groupMain, pwEncoder);
        author = user;
        authorName = user.getName();
        password = null;
    }
}