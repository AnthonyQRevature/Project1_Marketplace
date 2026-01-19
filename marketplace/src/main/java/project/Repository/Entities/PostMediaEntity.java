package project.Repository.Entities;

import java.util.Objects;

import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.type.PostgreSQLEnumJdbcType;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="post_media")
public class PostMediaEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Integer id;
    public Integer postId;
    @Nonnull
    public String mediaUrl;
    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    public MediaTypeEnum mediaType;

    public PostMediaEntity(Integer id, MediaTypeEnum mediaType, String mediaUrl, Integer postId) {
        this.id = id;
        this.mediaType = mediaType;
        this.mediaUrl = mediaUrl;
        this.postId = postId;
    }

    public PostMediaEntity() {
    }

    public PostMediaEntity(MediaTypeEnum mediaType, String mediaUrl, Integer postId) {
        this.mediaType = mediaType;
        this.mediaUrl = mediaUrl;
        this.postId = postId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("PostMediaEntity{");
        sb.append("postId=").append(postId);
        sb.append(", mediaUrl=").append(mediaUrl);
        sb.append(", mediaType=").append(mediaType);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.postId);
        hash = 79 * hash + Objects.hashCode(this.mediaUrl);
        hash = 79 * hash + Objects.hashCode(this.mediaType);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PostMediaEntity other = (PostMediaEntity) obj;
        if (!Objects.equals(this.mediaUrl, other.mediaUrl)) {
            return false;
        }
        if (!Objects.equals(this.postId, other.postId)) {
            return false;
        }
        return this.mediaType == other.mediaType;
    }
    public static enum MediaTypeEnum
    {
        image,
        video
    }

    
}
