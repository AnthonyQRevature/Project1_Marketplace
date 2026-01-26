package project.Repository.Entities;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.type.PostgreSQLEnumJdbcType;

import jakarta.annotation.Nonnull;

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
}
