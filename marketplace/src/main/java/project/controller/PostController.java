package project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import project.Repository.Entities.PostEntity;
import project.service.PostService;
import project.util.AllowCORS;

@RestController
@AllowCORS
public class PostController {

    PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }    

    @GetMapping("/listings")
    public ResponseEntity<List<PostEntity>> getAllPosts()
    {
        return ResponseEntity.ok(postService.getAllPosts());
    }
}
