package org.education.network.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.education.network.dto.request.DeleteMediaDto;
import org.education.network.dto.request.PostDto;
import org.education.network.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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

    @PostMapping("/create")
    public ResponseEntity createPost(@ModelAttribute PostDto postDto, Principal principal) {
        return postService.createPost(postDto, principal.getName());
    }

    @GetMapping
    public ResponseEntity getAllPostsByEmail(Principal principal) {
        return postService.getAllPostsByEmail(principal.getName());
    }

    @GetMapping(value = "/{postId}")
    public ResponseEntity getPost(@PathVariable("postId") String postId) {
        return postService.getPost(UUID.fromString(postId));
    }

    @Operation(
            summary = "delete user profile file")
    @DeleteMapping(value = "/file")
    public ResponseEntity deletePicture(@RequestBody DeleteMediaDto deleteMediaDto, Principal principal) {
        return postService.deleteFile(deleteMediaDto, principal.getName());
    }
}
