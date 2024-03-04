package org.education.network.web.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.education.network.dto.request.LikeRequestDto;
import org.education.network.dto.request.PostDto;
import org.education.network.service.LikeService;
import org.education.network.service.PostService;
import org.education.network.util.ResponseEntityUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
@Tag(name = "PostController", description = "crud of post")
public class PostController {

    private final PostService postService;
    private final LikeService likeService;

    @SneakyThrows
    @PostMapping("/like")
    public ResponseEntity createLike(@RequestBody LikeRequestDto like, Principal principal) {
        likeService.likeOperation(like, principal.getName());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/create")
    public ResponseEntity createPost(@ModelAttribute PostDto postDto, Principal principal) {
        return ResponseEntityUtil.get(HttpStatus.OK, postService.createPost(postDto, principal.getName()));
    }

    @GetMapping
    public ResponseEntity getAllPostsByEmail(Principal principal) {
        return ResponseEntityUtil.get(HttpStatus.OK, postService.getAllPostsDtoByEmail(principal.getName()));
    }

    @GetMapping(value = "/{postId}")
    public ResponseEntity getPost(@PathVariable("postId") String postId) {
        return ResponseEntityUtil.get(HttpStatus.OK, postService.getPostDto(UUID.fromString(postId)));
    }

}
