package project.Repository.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name="post_media")
public class PostMediaEntity {
	@Column(name="id")
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer post_id;

	@Column(name="post_id")
	private Integer postID;
	@Column(name="media_url")
	private String media_url;

	static enum MediaTypeEnum
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

	public boolean equals(Object object) {
		if (object == null || getClass() != object.getClass()) return false;
		if (!super.equals(object)) return false;

		PostMediaEntity that = (PostMediaEntity) object;
		return java.util.Objects.equals(post_id, that.post_id) && java.util.Objects.equals(postID, that.postID) && java.util.Objects.equals(media_url, that.media_url) && mediaTypeEnum == that.mediaTypeEnum;
	}

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