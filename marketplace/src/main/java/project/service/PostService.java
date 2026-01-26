package project.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.Repository.Entities.PostEntity;
import project.Repository.Entities.PostTagsEntity;
import project.Repository.Entities.UserDistanceEntity;
import project.Repository.Entities.UserProfileEntity;
import project.Repository.dao.PostDao;
import project.Repository.dao.UserDao;

@Service
public class PostService {
    PostDao dao;
    UserDao userDao;
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

    public List<PostEntity> getAllPostsWithinDistance(double distance, UserProfileEntity userEntityMakingRequest){
        List<UserDistanceEntity> usersWithinDistance = userDao.findUserByDistance(userEntityMakingRequest.getLatitude(), userEntityMakingRequest.getLongitude(), distance);
        List<PostEntity> postsWithinDistance = new ArrayList<>();
        for (UserDistanceEntity seller : usersWithinDistance){
            postsWithinDistance.addAll(dao.findPostBySellerId(seller.getUser_id()));
        }
        return postsWithinDistance;
    }

    public List<PostEntity> removePostsFromListWithoutTheseTags(List<PostEntity> posts, List<Integer> wantedTagIds, Integer distance){
        List<PostEntity> finalPostList;
        finalPostList = posts.stream()
            .filter((PostEntity e) -> 
                wantedTagIds.stream().allMatch(
                    (Integer id) -> e.tags.stream().anyMatch(
                        (PostTagsEntity t) -> t.getTag().equals(id)
                    )
                )
            ).toList();
        return finalPostList;
    }



}


