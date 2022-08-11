package your.dream.superboard.authentication.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import your.dream.superboard.authentication.data.RefreshToken;
import your.dream.superboard.authentication.repository.RefreshTokenRepository;
import your.dream.superboard.authentication.response.JwtResponse;
import your.dream.superboard.common.RandomGenerator;
import your.dream.superboard.users.user.data.UserAuthentication;
import your.dream.superboard.users.user.service.UserService;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtEncoder jwtEncoder;
    private static final String NAVER = "NV";
    private static final String KAKAO = "KKO";
    private static final String USER = "USER";
    private static final String SELF = "self";

    private static final String TOKEN_TYPE = "bearer";
    private static final String PRIVATE_CLAIM_SCOPE = "scope";

    private static final Long EXPIRY = 3600L;
    private static final Long REFRESH_EXPIRY = 2628000L;

    public JwtResponse issueUserJwt(Authentication authentication){
        LocalDateTime now = LocalDateTime.now();
        String subject = USER + "|" + authentication.getName();
        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        return new JwtResponse(
                accessToken(SELF, subject, scope, now),
                makeRefreshToken(subject,now),
                EXPIRY,
                REFRESH_EXPIRY,
                TOKEN_TYPE);
    }

    private String accessToken(String issuer, String subject, String scope, LocalDateTime now){
        Instant utc_now = now.toInstant(ZoneOffset.UTC);

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(issuer)
                .subject(subject)
                .issuedAt(utc_now)
                .expiresAt(utc_now.plusSeconds(EXPIRY))
                .claim(PRIVATE_CLAIM_SCOPE, scope)
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    private String makeRefreshToken(String subject, LocalDateTime now){
        try {
            refreshTokenRepository.updateEnabledBySubject(false,subject);
            RefreshToken refreshToken = new RefreshToken(
                    subject,
                    RandomGenerator.Alpha(128),
                    now,
                    REFRESH_EXPIRY);
            refreshTokenRepository.save(refreshToken);
            return refreshToken.getToken();
        } catch (Exception e){
            System.out.println(e);
            return null;
        }
    }

    private String rotateRefreshToken(String token, LocalDateTime now){
        RefreshToken oldToken = refreshTokenRepository.findByToken(token).orElseThrow();
        if (!oldToken.getEnabled()) {
            // refresh token leaked
            // disable all tokens on subject
            // and throw exception
            // TODO
        }
        String subject = oldToken.getSubject();
        return makeRefreshToken(subject, now);
    }
}
