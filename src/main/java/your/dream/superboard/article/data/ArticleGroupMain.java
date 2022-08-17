package your.dream.superboard.article.data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import your.dream.superboard.article.data.domain.MainGroupType;
import your.dream.superboard.article.request.MainGroupRequest;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "article_group_main", indexes = {
        @Index(name = "idx_articlegroupmain_path", columnList = "path")
})
public class ArticleGroupMain {
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

    @OneToMany(mappedBy = "groupMain", cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, orphanRemoval = true)
    @OrderBy("id DESC")
    private List<Article> articles = new ArrayList<>();

    @OneToMany(mappedBy = "main", cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, orphanRemoval = true)
    @OrderBy("id DESC")
    private List<ArticleGroupSub> subs = new ArrayList<>();

    @Enumerated
    @Column(name = "type", nullable = false)
    private MainGroupType type;

    @OneToOne(mappedBy = "group", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, optional = false, orphanRemoval = true)
    private ArticleGroupMainRole role;

    @Column(name = "visible", nullable = false)
    private boolean visible;

    @Column(name = "etc_group_name", length = 16)
    private String etcGroupName;

    public ArticleGroupMain(MainGroupRequest request){
        Instant now = Instant.now();
        this.path = request.getPath();
        this.name = request.getName();
        this.type = MainGroupType.valueOf(request.getType());
        this.role = new ArticleGroupMainRole(this);
        etcGroupName = null;
        createdAt = now;
    }
}