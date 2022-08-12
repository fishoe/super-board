package your.dream.superboard.users.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import your.dream.superboard.users.user.request.UserRequest;
import your.dream.superboard.users.user.response.UserResponse;
import your.dream.superboard.users.user.service.UserService;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> signUpUser(
            @RequestBody UserRequest userRequest
    ){
        UserResponse userResponse = userService.createUser(userRequest);
        return ResponseEntity.ok(userResponse);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<?> userPersonal(
            Authentication authentication
    ){
        return ResponseEntity.ok(authentication.getName());
    }

    @GetMapping("/sample")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<?> needAuth(
    ){
        return ResponseEntity.ok("congratulation");
    }
}
