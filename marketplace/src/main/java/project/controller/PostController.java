package project.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import project.Repository.Entities.PostEntity;
import project.Repository.Entities.PostMediaEntity;
import project.Repository.Entities.PostTagsEntity;
import project.controller.request.CreatePostRequest;
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

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/listings")
    @ResponseBody
    public ResponseEntity<List<PostEntity>> getPostsByTag(
            @RequestParam(name = "tags", required = false) List<Integer> tagIds,
            @RequestParam(name = "distance", required = false) Integer distance){
        System.out.println(distance);

        if ((tagIds==null) || (distance==null)){
            return ResponseEntity.ok(postService.getAllPosts());
        }
        for (Integer t:tagIds){
            System.out.println(t);
        }
        return ResponseEntity.ok(postService.removePostsFromListWithoutTheseTags(postService.getAllPosts(), tagIds, distance));
    }

    @PostMapping("/createpost")
    public PostEntity createPost(@RequestParam Integer sellerId, @RequestBody CreatePostRequest request) {
        return postService.createPost(sellerId, request);
    }

}


