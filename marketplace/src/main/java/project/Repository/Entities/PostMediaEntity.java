package project.Repository.Entities;

import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.type.PostgreSQLEnumJdbcType;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="post_media")
public class PostMediaEntity {
    public static enum MediaTypeEnum
    {
        image,
        video
    }

    @Id
    //@ManyToOne(targetEntity=PostEntity.class)
    public Integer postId;
    @Nonnull
    public String mediaUrl;
    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    public MediaTypeEnum mediaType;
}
