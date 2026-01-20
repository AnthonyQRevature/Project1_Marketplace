package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import project.Repository.Entities.UserProfileEntity;
import project.Repository.dao.UserProfileDao;

@Service
public class UserProfileService {
    private UserProfileDao dao;

    @Autowired
    public UserProfileService(UserProfileDao dao) 
    {
        this.dao = dao;
    }

    public UserProfileEntity addMedia(MultipartFile file, Integer user_id)
    {
        throw new RuntimeException("Not yet implemented");
    }
}
