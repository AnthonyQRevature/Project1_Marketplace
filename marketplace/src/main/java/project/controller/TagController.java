package project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import project.Repository.Entities.TagEntity;
import project.service.TagService;
import project.util.AllowCORS;

@RestController
@AllowCORS
public class TagController {

    TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/tags")
    public List<TagEntity> getAll()
    {
        return tagService.getAllTags();
    }
}
