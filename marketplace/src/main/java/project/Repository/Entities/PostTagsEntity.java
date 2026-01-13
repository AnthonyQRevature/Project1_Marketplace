
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
@Table(name="post_tags")
public class PostTagsEntity
{
	public static class PostTagsID
	{
		private Integer postID;
		private Integer tagID;

		public PostTagsID(Integer postID, Integer tagID) {
			this.postID = postID;
			this.tagID = tagID;
		}

		public boolean equals(Object object) {
			if (object == null || getClass() != object.getClass()) return false;
			if (!super.equals(object)) return false;
			PostTagsID that = (PostTagsID) object;
			return java.util.Objects.equals(postID, that.postID) && java.util.Objects.equals(tagID, that.tagID);
		}

		public int hashCode() {
			return Objects.hash(super.hashCode(), postID, tagID);
		}
	}

	@Id
	@Column(name="post_id")
	private Integer post;
	@Id
	@Column(name="tag_id")
	private Integer tag;

	public PostTagsEntity() {}

	public PostTagsEntity(PostTagsEntity postTagsEntity) {
		this.post = postTagsEntity.post;
		this.tag = postTagsEntity.tag;
	}

	public PostTagsEntity(Integer post, Integer tag) {
		this.post = post;
		this.tag = tag;
	}

	public Integer getPost() {return post;}
	public void setPost(Integer post) {this.post = post;}
	public Integer getTag() {return tag;}
	public void setTag(Integer tag) {this.tag = tag;}

	public boolean equals(Object object) {
		if (object == null || getClass() != object.getClass()) return false;
		if (!super.equals(object)) return false;
		PostTagsEntity that = (PostTagsEntity) object;
		return java.util.Objects.equals(post, that.post) && java.util.Objects.equals(tag, that.tag);
	}

	public int hashCode() {
		return Objects.hash(super.hashCode(), post, tag);
	}

	@java.lang.Override
	public java.lang.String toString() {
		return "PostTagsEntity{" +
				"post=" + post +
				", tag=" + tag +
				'}';
	}
}

/*
    post_id INT REFERENCES post(id) ON DELETE CASCADE,
    tag_id INT REFERENCES tags(id) ON DELETE CASCADE,
    PRIMARY KEY (post_id, tag_id)
 */