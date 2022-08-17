package your.dream.superboard.article.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import your.dream.superboard.article.request.MainGroupRequest;
import your.dream.superboard.article.response.MainGroupResponse;
import your.dream.superboard.article.service.GroupService;

import javax.validation.Valid;

import static your.dream.superboard.common.Path.API_PREFIX;
import static your.dream.superboard.common.Path.GROUP;

@RestController
@RequestMapping(value = API_PREFIX + GROUP)
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;

    @PostMapping
    public ResponseEntity<MainGroupResponse> postGroup(@Valid @RequestBody MainGroupRequest request){
        var response = groupService.createMainGroup(request);
        return ResponseEntity.ok(response);
    }
}
