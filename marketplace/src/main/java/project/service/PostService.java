package project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.Repository.Entities.PostEntity;
import project.Repository.dao.PostDao;

@Service
public class PostService {
    PostDao dao;

    @Autowired
    public PostService(PostDao dao) {
        this.dao = dao;
    }

    //maybe add limit and offset
    public List<PostEntity> getAllPosts()
    {
        var all = dao.findAll();
        return all;
    }
}


