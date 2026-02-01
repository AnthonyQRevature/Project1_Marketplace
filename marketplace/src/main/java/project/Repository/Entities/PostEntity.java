package project.Repository.Entities;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.type.PostgreSQLEnumJdbcType;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="post")
public class PostEntity {
    public static enum PostStatusEnum
    {
        available,
        unlisted,
        sold
    }

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Integer id;
    //@ManyToOne(targetEntity=UserEntity.class)
    //@JoinColumn(name="seller_id")
    public Integer sellerId;
    @Nonnull
    public String description;
    @Nonnull
    public double price;
    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    public PostStatusEnum status;
    public Timestamp createdAt;
    public Timestamp lastEditTime;

    //collections
    @OneToMany(targetEntity=PostMediaEntity.class, mappedBy="postId")
    public List<PostMediaEntity> media;

    @OneToMany(targetEntity=PostTagsEntity.class, mappedBy="post")
    public List<PostTagsEntity> tags;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }

    @Nonnull
    public String getDescription() {
        return description;
    }

    public void setDescription(@Nonnull String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public PostStatusEnum getStatus() {
        return status;
    }

    public void setStatus(PostStatusEnum status) {
        this.status = status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getLastEditTime() {
        return lastEditTime;
    }

    public void setLastEditTime(Timestamp lastEditTime) {
        this.lastEditTime = lastEditTime;
    }

    public List<PostMediaEntity> getMedia() {
        return media;
    }

    public void setMedia(List<PostMediaEntity> media) {
        this.media = media;
    }

    public List<PostTagsEntity> getTags() {
        return tags;
    }

    public void setTags(List<PostTagsEntity> tags) {
        this.tags = tags;
    }
}
