package project.Repository.Entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name="review")
public class ReviewEntity {
	@Column(name="id")
	@Id
	/*
	 * relies on a database's auto-increment feature
	 */
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer reviewId;
	@Column(name="reviewer_id")
	private Integer reviewerID;
	@Column(name="seller_id")
	private Integer sellerID;
	@Column(name="post_id")
	private Integer post_ID;
	@Column(name="rating")
	private Integer rating;
	@Column(name="comment")
	private String text;
	@Column(name="created_at")
	private Instant createdAt;
	/*
	id SERIAL PRIMARY KEY,
	reviewer_id INT REFERENCES users(id) ON DELETE CASCADE,
	seller_id INT REFERENCES users(id) ON DELETE CASCADE,
	post_id INT REFERENCES post(id) ON DELETE CASCADE,
	rating INT CHECK (rating BETWEEN 1 AND 5),
	comment TEXT,
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
*/
	public ReviewEntity() {}

	public ReviewEntity(ReviewEntity reviewEntity) {
		this.reviewId = reviewEntity.reviewId;
		this.reviewerID = reviewEntity.reviewerID;
		this.sellerID = reviewEntity.sellerID;
		this.post_ID = reviewEntity.post_ID;
		this.rating = reviewEntity.rating;
		this.text = reviewEntity.text;
		this.createdAt = reviewEntity.createdAt;
	}


	public ReviewEntity(Integer reviewId, Integer reviewerID, Integer sellerID, Integer post_ID, Integer rating, String text, Instant createdAt) {
		this.reviewId = reviewId;
		this.reviewerID = reviewerID;
		this.sellerID = sellerID;
		this.post_ID = post_ID;
		this.rating = rating;
		this.text = text;
		this.createdAt = createdAt;
	}

	//getters and setters
	public Integer getReviewId() {return reviewId;}
	public void setReviewId(Integer reviewId) {this.reviewId = reviewId;}
	public Integer getReviewerID() {return reviewerID;}
	public void setReviewerID(Integer reviewerID) {this.reviewerID = reviewerID;}
	public Integer getSellerID() {return sellerID;}
	public void setSellerID(Integer sellerID) {this.sellerID = sellerID;}
	public Integer getPost_ID() {return post_ID;}
	public void setPost_ID(Integer post_ID) {this.post_ID = post_ID;}
	public Integer getRating() {return rating;}
	public void setRating(Integer rating) {this.rating = rating;}
	public String getText() {return text;}
	public void setText(String text) {this.text = text;}
	public Instant getCreatedAt() {return createdAt;}
	public void setCreatedAt(Instant createdAt) {this.createdAt = createdAt;}

	//equals, hash, toString,
	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		ReviewEntity that = (ReviewEntity) o;
		return Objects.equals(reviewId, that.reviewId) && Objects.equals(reviewerID, that.reviewerID) && Objects.equals(sellerID, that.sellerID) && Objects.equals(post_ID, that.post_ID) && Objects.equals(rating, that.rating) && Objects.equals(text, that.text) && Objects.equals(createdAt, that.createdAt);
	}

	@Override
	public int hashCode() {
		return Objects.hash(reviewId, reviewerID, sellerID, post_ID, rating, text, createdAt);
	}

	@Override
	public String toString() {
		return "ReviewEntity{" +
				"reviewId=" + reviewId +
				", reviewerID=" + reviewerID +
				", sellerID=" + sellerID +
				", post_ID=" + post_ID +
				", rating=" + rating +
				", text='" + text + '\'' +
				", createdAt=" + createdAt +
				'}';
	}
}