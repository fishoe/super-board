package your.dream.superboard.authentication.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import your.dream.superboard.authentication.response.JwtResponse;
import your.dream.superboard.authentication.service.AuthenticationService;
import your.dream.superboard.users.user.service.UserService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final UserService userService;
    private final AuthenticationService authenticationService;
    @PostMapping
    public ResponseEntity<JwtResponse> userSignIn(Authentication authentication){
        JwtResponse jwtResponse = authenticationService.issueUserJwt(authentication);
        return ResponseEntity.ok(jwtResponse);
    }

}
