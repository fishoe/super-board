package your.dream.superboard.article.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import your.dream.superboard.article.data.ArticleGroupMain;
import your.dream.superboard.article.data.ArticleGroupMainRole;
import your.dream.superboard.article.data.ArticleGroupSub;
import your.dream.superboard.article.data.domain.GroupRoleType;
import your.dream.superboard.article.exception.DuplicatedArticleGroup;
import your.dream.superboard.article.repository.ArticleGroupMainRepository;
import your.dream.superboard.article.repository.ArticleGroupSubRepository;
import your.dream.superboard.article.request.MainGroupRequest;
import your.dream.superboard.article.request.SubGroupRequest;
import your.dream.superboard.article.response.MainGroupResponse;

import java.util.*;

import static your.dream.superboard.article.data.domain.GroupRoleType.*;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final ArticleGroupMainRepository mainGroupRepository;
    private final ArticleGroupSubRepository subGroupRepository;

    public ArticleGroupMain findMainGroup(String path){
        return mainGroupRepository.findByPath(path).orElseThrow();
    }

    public ArticleGroupSub findSubGroup(ArticleGroupMain main, String code){
        return subGroupRepository.findByMainAndCode(main, code).orElseThrow();
    }

    @Transactional
    public MainGroupResponse createMainGroup(MainGroupRequest request){
        if (mainGroupRepository.existsByPath(request.getPath()))
            throw new DuplicatedArticleGroup();
        var group = new ArticleGroupMain(request);
        mainGroupRepository.save(group);
        return new MainGroupResponse();
    }

    @Transactional
    public SubGroupRequest createSubGroup(String mainGroupPath, SubGroupRequest request){
        ArticleGroupMain mainGroup = findMainGroup(mainGroupPath);
        if (subGroupRepository.existsByMainAndCode(mainGroup, request.getCode()))
            throw new DuplicatedArticleGroup();
        var group = new ArticleGroupSub(mainGroup, request);
        subGroupRepository.save(group);
        return new SubGroupRequest();
    }

    public boolean CheckPermissionWrite(
            ArticleGroupMain mainGroup,
            Authentication authentication
    ){
        String action = "WRITE";
        var mainGroupPath = mainGroup.getPath();
        var mainRole = mainGroup.getRole().getWriteAuthority();

        return hasAuthority(mainGroupPath,authentication,mainRole,action);
    }

    public boolean CheckPermissionWrite(
        ArticleGroupMain mainGroup,
        ArticleGroupSub subGroup,
        Authentication authentication
    ){
        String action = "WRITE";
        var mainGroupPath = mainGroup.getPath();
        var subGroupCode = subGroup.getCode();
        var subRole = subGroup.getRole().getWriteAuthority();

        return hasAuthority(mainGroupPath + "." + subGroupCode, authentication, subRole, action);
    }

    public boolean CheckPermissionRead(
            ArticleGroupMain mainGroup,
            Authentication authentication
    ){
        String action = "Read";
        var mainGroupPath = mainGroup.getPath();
        var mainRole = mainGroup.getRole().getReadAuthority();

        return hasAuthority(mainGroupPath,authentication,mainRole,action);
    }

    public boolean CheckPermissionRead(
            ArticleGroupMain mainGroup,
            ArticleGroupSub subGroup,
            Authentication authentication
    ){
        String action = "Read";
        var mainGroupPath = mainGroup.getPath();
        var subGroupCode = subGroup.getCode();
        var subRole = subGroup.getRole().getReadAuthority();

        return hasAuthority(mainGroupPath + "." + subGroupCode, authentication, subRole, action);
    }


    private boolean hasAuthority(
            String group,
            Authentication authentication,
            GroupRoleType roleType,
            String action)
    {
        if (roleType.equals(ANONYMOUS))
            return true;

        if (authentication == null)
            return false;
        else if (roleType.equals(USER))
            return true;

        var authorities = authentication.getAuthorities();
        var requiredRoles = getRequiredRole(roleType, group, action);
        return hasAuthority(authorities, requiredRoles);
    }

    private boolean hasAuthority(Collection<? extends GrantedAuthority> authorities, List<String> requiredRoles){
        for (String role:requiredRoles) {
            for (GrantedAuthority grantedAuthority : authorities) {
                if (role.equals(grantedAuthority.getAuthority())) {
                    return true;
                }
            }
        }
        return false;
    }

    private List<String> getRequiredRole(GroupRoleType type, String group, String action){
        List<String> requires = new ArrayList<>();
        requires.add("ROLE_ADMIN");
        if (type.equals(ADMIN))
            return requires;
        requires.add("ROLE_" + group + ":MANAGER");
        if (type.equals(MANAGER))
            return requires;
        requires.add("ROLE_" + group + ":SUPPORTER");
        if (type.equals(SUPPORTER))
            return requires;
        requires.add("ROLE_" + group + ":" + action);
        return requires;
    }
}
