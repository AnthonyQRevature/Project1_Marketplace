package project.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.Repository.Entities.*;
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

    public List<PostEntity> removePostsFromListWithoutTheseTags(List<PostEntity> posts, List<Integer> wantedTagIds){
        List<PostEntity> finalPostList = new ArrayList<>();
        for (PostEntity post : posts){
            for (PostTagsEntity postTag : post.tags){
                if (wantedTagIds.contains(postTag.getTag())){
                    finalPostList.add(post);
                }
            }
        }
        return finalPostList;
    }


}


