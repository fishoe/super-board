package your.dream.superboard.users.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import your.dream.superboard.users.user.exception.InvalidUserRequest;
import your.dream.superboard.users.user.request.UserRequest;
import your.dream.superboard.users.user.response.UserResponse;
import your.dream.superboard.users.user.service.UserService;

import javax.validation.Valid;
import java.net.URI;

import static your.dream.superboard.common.Path.API_PREFIX;
import static your.dream.superboard.common.Path.USER;

@RestController
@RequestMapping(API_PREFIX + USER)
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> signUpUser(
            @Valid @RequestBody UserRequest userRequest, Errors errors
    ){
        if (errors.hasErrors())
            throw new InvalidUserRequest(errors);
        UserResponse userResponse = userService.createUser(userRequest);
        String user_uri = API_PREFIX + USER + "/" + userResponse.getUsername();
        return ResponseEntity.created(URI.create(user_uri)).body(userResponse);
    }

    @GetMapping(path = "/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> getUserProfile(
            @PathVariable Long userId
    ){

        return ResponseEntity.ok(userService.findUserPersonalByAdmin(userId));
    }
    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<?> userPersonal(
            Authentication authentication
    ){
        return ResponseEntity.ok(authentication.getName());
    }
}
