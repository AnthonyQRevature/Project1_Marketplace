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
@Table(name="tags")
public class TagsEntity {
	@Column(name="id")
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer post_id;

	@Column(name="tag_name")
	private String tag_name;

	public TagsEntity() {}

	public TagsEntity(TagsEntity tagsEntity) {
		this.post_id = tagsEntity.post_id;
		this.tag_name = tagsEntity.tag_name;
	}

	public TagsEntity(Integer post_id, String tag_name) {
		this.post_id = post_id;
		this.tag_name = tag_name;
	}

	public Integer getPost_id() {return post_id;}
	public void setPost_id(Integer post_id) {this.post_id = post_id;}
	public String getTag_name() {return tag_name;}
	public void setTag_name(String tag_name) {this.tag_name = tag_name;}

	public boolean equals(Object object) {
		if (object == null || getClass() != object.getClass()) return false;
		if (!super.equals(object)) return false;
		TagsEntity that = (TagsEntity) object;
		return java.util.Objects.equals(post_id, that.post_id) && java.util.Objects.equals(tag_name, that.tag_name);
	}

	public int hashCode() {
		return Objects.hash(super.hashCode(), post_id, tag_name);
	}

	@java.lang.Override
	public java.lang.String toString() {
		return "TagsEntity{" +
				"post_id=" + post_id +
				", tag_name='" + tag_name + '\'' +
				'}';
	}
}

/*
    id SERIAL PRIMARY KEY,
    tag_name VARCHAR(50) UNIQUE NOT NULL
 */