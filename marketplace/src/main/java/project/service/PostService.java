package project.service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import project.Repository.Entities.*;
import project.Repository.dao.PostDao;
import project.Repository.dao.PostMediaDao;
import project.Repository.dao.PostTagsDao;
import project.Repository.dao.UserDao;
import project.controller.request.CreatePostRequest;

@Service
public class PostService {
    PostDao dao;
    UserDao userDao;
    PostMediaDao postMediaDao;
    PostTagsDao postTagsDao;
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




    @Transactional
    public PostEntity createPost(Integer sellerId, CreatePostRequest request) {
        //create PostEntity
        PostEntity newPost = new PostEntity();
        newPost.setSellerId(sellerId);
        newPost.setDescription(request.getDescription());
        newPost.setPrice(request.getPrice());
        newPost.setStatus(PostEntity.PostStatusEnum.valueOf(request.getStatus()));
        newPost = dao.save(newPost);

        //save media
        if (request.getMedia() != null) {
            for (CreatePostRequest.Media m : request.getMedia()) {
                PostMediaEntity media = new PostMediaEntity();
                media.setPostId(newPost.getId());
                media.setMediaEncoded(m.getMedia_encoded());
                media.setMediaType(PostMediaEntity.MediaTypeEnum.valueOf(m.getMedia_type()));
                postMediaDao.save(media);
            }
        }

        //save tags
        if (request.getTags() != null) {
            for (Integer tagId : request.getTags()) {
                PostTagsEntity tagLink = new PostTagsEntity(newPost.getId(), tagId);
                postTagsDao.save(tagLink);
            }
        }

        return newPost;
    }
}





