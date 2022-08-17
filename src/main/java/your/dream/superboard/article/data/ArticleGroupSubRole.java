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
@Table(name = "article_group_sub_role")
public class ArticleGroupSubRole {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, optional = false, orphanRemoval = true)
    @JoinColumn(name = "group_id", nullable = false)
    private ArticleGroupSub group;

    @Enumerated
    @Column(name = "write_authority", nullable = false)
    private GroupRoleType writeAuthority;

    @Enumerated
    @Column(name = "read_authority", nullable = false)
    private GroupRoleType readAuthority;

    @Column(name = "deletable", nullable = false)
    private boolean deletable;

    @Column(name = "modifiable", nullable = false)
    private boolean modifiable;

    public ArticleGroupSubRole(ArticleGroupSub group){
        this.group = group;
        var mainRole = group.getMain().getRole();
        readAuthority = mainRole.getReadAuthority();
        writeAuthority = mainRole.getWriteAuthority();
        modifiable = mainRole.isModifiable();
        deletable = mainRole.isDeletable();
    }
}