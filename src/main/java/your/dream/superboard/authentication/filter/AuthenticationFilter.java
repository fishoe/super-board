package your.dream.superboard.authentication.filter;

import com.nimbusds.jose.util.StandardCharset;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.util.StreamUtils;
import your.dream.superboard.authentication.request.UsernamePasswordRequest;
import your.dream.superboard.authentication.token.CommonAuthenticationToken;
import your.dream.superboard.users.user.authentication.UserAuthenticationToken;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static your.dream.superboard.common.Path.API_PREFIX;
import static your.dream.superboard.common.Path.AUTH;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final static String ALLOW_HTTP_METHOD = "POST";
    private final static String ALLOW_CONTENT_TYPE = "application/json";

    public AuthenticationFilter(AuthenticationManager authenticationManager)
    {
        super(authenticationManager);
        setRequiresAuthenticationRequestMatcher(
                new OrRequestMatcher(
                        new AntPathRequestMatcher(API_PREFIX + AUTH)
                )
        );

        setAuthenticationSuccessHandler((request, response, authentication) -> {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
            }
        });
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException
    {
        if (!request.getMethod().equals(ALLOW_HTTP_METHOD)) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        if (!request.getContentType().equals(ALLOW_CONTENT_TYPE)) {
            throw new AuthenticationServiceException("content-type only allowed of application/json: " + request.getContentType());
        }
        String username;
        String password;
        try {
            String request_body = StreamUtils.copyToString(request.getInputStream(), StandardCharset.UTF_8);
            UsernamePasswordRequest UsernamePassword = UsernamePasswordRequest.ConvertFromString(request_body);
            username = UsernamePassword.getUsername().trim();
            password = (UsernamePassword.getPassword() != null) ? UsernamePassword.getPassword() : "" ;
        } catch ( Exception e ) {
            throw new BadCredentialsException("invalid credential request");
        }
        Authentication authentication;
        try {
            authentication = CommonAuthenticationToken.unauthenticated(
                    UserAuthenticationToken.class, username, password
            );
        } catch (Exception e) {
            throw new AuthenticationServiceException("occurred a problem while creating token");
        }
        return this.getAuthenticationManager().authenticate(authentication);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authResult);
        SecurityContextHolder.setContext(context);
        chain.doFilter(request, response);
    }

}

