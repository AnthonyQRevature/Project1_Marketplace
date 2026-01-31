package project.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import project.Repository.Entities.PostEntity;
import project.Repository.Entities.PostMediaEntity;
import project.Repository.Entities.PostTagsEntity;
import project.Repository.Entities.TagEntity;
import project.Repository.Entities.UserDistanceEntity;
import project.Repository.Entities.UserProfileEntity;
import project.Repository.dao.PostDao;
import project.Repository.dao.PostMediaDao;
import project.Repository.dao.PostTagsDao;
import project.Repository.dao.TagDao;
import project.Repository.dao.UserDao;
import project.controller.request.CreatePostRequest;
import project.controller.response.ListingResponse;

@Service
public class PostService {
    private final PostDao dao;
    private final UserDao userDao;
    private final PostMediaDao postMediaDao;
    private final PostTagsDao postTagsDao;
    private final TagDao tagDao;
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
        response.sellerId = post.sellerId;

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






