package project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/listings")
    @ResponseBody
    public ResponseEntity<List<PostEntity>> getPostsByTag(
            @RequestParam(name = "tags", required = false) List<Integer> tagIds,
            @RequestParam(name = "distance", required = false) Integer distance
    ) {
        if ((tagIds==null) || (distance==null)){
            return ResponseEntity.ok(postService.getAllPosts());
        }
        return ResponseEntity.ok(postService.removePostsFromListWithoutTheseTags(postService.getAllPosts(), tagIds, distance));
    }


}


