package project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.Repository.Entities.TagEntity;
import project.Repository.dao.TagDao;

@Service
public class TagService {

    TagDao dao;

    @Autowired
    TagService(TagDao dao)
    {
        this.dao = dao;
    }

    public List<TagEntity> getAllTags()
    {
        return dao.findAll();
    }
}
