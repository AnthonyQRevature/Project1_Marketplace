package project.Repository.Entities;

import java.util.Objects;

import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.type.PostgreSQLEnumJdbcType;

<<<<<<< HEAD
import jakarta.annotation.Nonnull;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
=======
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
>>>>>>> 86cf0c65615775d533fa1fd05d2e82332ac5d6fb
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="post_media")
public class PostMediaEntity {
<<<<<<< HEAD

    @Id
    public Integer id;
    //@ManyToOne(targetEntity=PostEntity.class)
    public Integer postId;
    @Nonnull
    public String mediaUrl;
    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    public MediaTypeEnum mediaType;

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
=======
	@Column(name="id")
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer post_id;

	@Column(name="post_id")
	private Integer postID;
	@Column(name="media_url")
	private String media_url;

	public static enum MediaTypeEnum
	{
		image,
		video
	}

	@Enumerated(EnumType.STRING)
	//Hibernate 6
	//otherwise we'd need to use native query
	@JdbcType(PostgreSQLEnumJdbcType.class)
	@Column(name="media_type")
	private MediaTypeEnum mediaTypeEnum;

	public PostMediaEntity() {}

	public PostMediaEntity(PostMediaEntity postMediaEntity) {
		this.post_id = postMediaEntity.post_id;
		this.postID = postMediaEntity.postID;
		this.media_url = postMediaEntity.media_url;
		this.mediaTypeEnum = postMediaEntity.mediaTypeEnum;
	}

	public Integer getPost_id() {return post_id;}
	public void setPost_id(Integer post_id) {this.post_id = post_id;}
	public Integer getPostID() {return postID;}
	public void setPostID(Integer postID) {this.postID = postID;}
	public String getMedia_url() {return media_url;}
	public void setMedia_url(String media_url) {this.media_url = media_url;}
	public MediaTypeEnum getMediaTypeEnum() {return mediaTypeEnum;}
	public void setMediaTypeEnum(MediaTypeEnum mediaTypeEnum) {this.mediaTypeEnum = mediaTypeEnum;}

	@Override
	public boolean equals(Object object) {
		if (object == null || getClass() != object.getClass()) return false;
		if (!super.equals(object)) return false;

		PostMediaEntity that = (PostMediaEntity) object;
		return java.util.Objects.equals(post_id, that.post_id) && java.util.Objects.equals(postID, that.postID) && java.util.Objects.equals(media_url, that.media_url) && mediaTypeEnum == that.mediaTypeEnum;
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + Objects.hashCode(post_id);
		result = 31 * result + Objects.hashCode(postID);
		result = 31 * result + Objects.hashCode(media_url);
		result = 31 * result + Objects.hashCode(mediaTypeEnum);
		return result;
	}

	@java.lang.Override
	public java.lang.String toString() {
		return "PostMediaEntity{" +
				"post_id=" + post_id +
				", postID=" + postID +
				", media_url='" + media_url + '\'' +
				", mediaTypeEnum=" + mediaTypeEnum +
				'}';
	}
}

/*
    id SERIAL PRIMARY KEY,
    post_id INT REFERENCES post(id) ON DELETE CASCADE,
    media_url VARCHAR(255) NOT NULL,
    media_type media_type_enum DEFAULT 'image'
 */
>>>>>>> 86cf0c65615775d533fa1fd05d2e82332ac5d6fb
