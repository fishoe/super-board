package your.dream.superboard.users.user.authentication;

import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import your.dream.superboard.users.user.data.UserAuthentication;

import java.util.ArrayList;
import java.util.Collection;

public class StoredUserCredit implements UserDetails, CredentialsContainer {

    private static final String ROLE_USER = "ROLE_USER";
    private final String username;
    private String password;
    Collection<GrantedAuthority> authorities;
    private final boolean enable;
    private final boolean isLocked;

    public StoredUserCredit(UserAuthentication user, boolean isLocked){
        username = user.getUsername();
        password = user.getPassword();
        authorities = new ArrayList<>();
        authorities.add(() -> ROLE_USER);
        user.getPersonal().getAuthorities()
                .forEach(authority -> authorities.add(authority::getAuthority));
        enable = user.getPersonal().getDeleted();
        this.isLocked = !isLocked;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enable;
    }

    @Override
    public void eraseCredentials() {
        password = null;
    }
}
