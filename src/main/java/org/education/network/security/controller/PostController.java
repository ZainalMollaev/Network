package org.education.network.security.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.education.network.dto.request.PostDto;
import org.education.network.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
@Tag(name = "PostController", description = "crud of post")
public class PostController {

    private final PostService postService;

    @PostMapping("/create")
    public ResponseEntity createPost(@ModelAttribute PostDto postDto) {
        return postService.createPost(postDto);
    }

    @GetMapping
    public ResponseEntity getAllPostsByEmail(@RequestParam("email") String email) {
        return postService.getAllPostsByEmail(email);
    }

    @GetMapping(value = "/{postId}")
    public ResponseEntity getPost(@PathVariable("postId") String postId) {
        return postService.getPost(postId);
    }

}
