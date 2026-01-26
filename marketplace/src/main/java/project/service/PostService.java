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
import project.Repository.dao.*;
import project.controller.request.CreatePostRequest;
import project.controller.response.ListingResponse;

@Service
public class PostService {
    PostDao dao;
    UserDao userDao;
    PostMediaDao postMediaDao;
    PostTagsDao postTagsDao;
    TagDao tagDao;
    @Autowired
    public PostService(PostDao dao, UserDao userDao, PostMediaDao postMediaDao, PostTagsDao postTagsDao, TagDao tagDao) {
        this.dao = dao;
        this.userDao = userDao;
        this.postMediaDao=postMediaDao;
        this.postTagsDao=postTagsDao;
        this.tagDao=tagDao;
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






    public ListingResponse getListingById(Integer id) {
        PostEntity post = dao.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        ListingResponse response = new ListingResponse();
        response.id = post.id;
        response.description = post.description;
        response.price = post.price;
        response.status = post.status.name();

        // Media
        List<PostMediaEntity> mediaEntities = postMediaDao.findByPostId(id);
        response.media = new ArrayList<>();
        for (PostMediaEntity m : mediaEntities) {
            response.media.add(
                    new ListingResponse.MediaResponse(
                            m.mediaEncoded,
                            m.mediaType.name()
                    )
            );
        }

        // Tags
        List<PostTagsEntity> postTags = postTagsDao.findByPost(id);
        response.tags = new ArrayList<>();
        for (PostTagsEntity pt : postTags) {
            TagEntity tag = tagDao.findById(pt.getTag()).orElse(null);
            if (tag != null) {
                response.tags.add(
                        new ListingResponse.TagResponse(
                                tag.getId(),
                                tag.getTag_name()
                        )
                );
            }
        }

        return response;
    }
}






