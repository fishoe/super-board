package your.dream.superboard.article.data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import your.dream.superboard.article.data.domain.GroupRoleType;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "article_group_main_role")
public class ArticleGroupMainRole {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, optional = false, orphanRemoval = true)
    @JoinColumn(name = "article_group_main_id", nullable = false)
    private ArticleGroupMain group;

    @Enumerated
    @Column(name = "catalog_authority", nullable = false)
    private GroupRoleType catalogAuthority;

    @Enumerated
    @Column(name = "read_authority", nullable = false)
    private GroupRoleType readAuthority;

    @Enumerated
    @Column(name = "write_authority", nullable = false)
    private GroupRoleType writeAuthority;

    @Column(name = "deletable", nullable = false)
    private boolean deletable;

    @Column(name = "modifiable", nullable = false)
    private boolean modifiable;

    public ArticleGroupMainRole(ArticleGroupMain mainGroup){
        catalogAuthority = GroupRoleType.ANONYMOUS;
        readAuthority = GroupRoleType.ANONYMOUS;
        writeAuthority = GroupRoleType.USER;
        deletable = true;
        modifiable = true;
        group = mainGroup;
    }
}