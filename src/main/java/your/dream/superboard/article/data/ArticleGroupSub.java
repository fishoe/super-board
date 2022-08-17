package your.dream.superboard.article.data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import your.dream.superboard.article.request.SubGroupRequest;

import javax.persistence.*;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "article_group_sub", uniqueConstraints = {
        @UniqueConstraint(name = "uc_articlegroupsub_main_code", columnNames = {"main_id", "code"})
})
public class ArticleGroupSub {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST, optional = false)
    @JoinColumn(name = "main_id", nullable = false)
    private ArticleGroupMain main;


    @Column(name = "name", nullable = false, length = 16)
    private String name;

    @OneToMany(mappedBy = "groupSub", orphanRemoval = true)
    private Set<Article> articles = new LinkedHashSet<>();

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "code", nullable = false, length = 20)
    private String code;

    @OneToOne(mappedBy = "group", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, orphanRemoval = true)
    private ArticleGroupSubRole role;

    public ArticleGroupSub(ArticleGroupMain mainGroup, SubGroupRequest request){
        Instant now = Instant.now();

        code = request.getCode();
        name = request.getName();
        createdAt = now;
        main = mainGroup;
        role = new ArticleGroupSubRole(this);
    }
}