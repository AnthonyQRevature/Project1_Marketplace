package project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import project.Repository.Entities.TagEntity;
import project.service.TagService;
import project.service.UserService;

@RestController
public class TagController {

    TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/tags")
    List<TagEntity> getAll()
    {
        return tagService.getAllTags();
    }
}
