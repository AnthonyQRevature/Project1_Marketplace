package project.Repository.Entities;

<<<<<<< HEAD
import java.sql.Timestamp;
import java.util.List;
=======
import java.time.Instant;
import java.util.Objects;
>>>>>>> 86cf0c65615775d533fa1fd05d2e82332ac5d6fb

import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.type.PostgreSQLEnumJdbcType;

<<<<<<< HEAD
import jakarta.annotation.Nonnull;
=======
import jakarta.persistence.Column;
>>>>>>> 86cf0c65615775d533fa1fd05d2e82332ac5d6fb
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
<<<<<<< HEAD
import jakarta.persistence.OneToMany;
=======
>>>>>>> 86cf0c65615775d533fa1fd05d2e82332ac5d6fb
import jakarta.persistence.Table;

@Entity
@Table(name="post")
public class PostEntity {
<<<<<<< HEAD
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
}
=======
	@Column(name="id")
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer post_id;

	@Column(name="last_edit_time")
	private Instant last_edit;
	@Column(name="created_at")
	private Instant created_at;
	@Column(name="seller_id")
	private Integer seller_id;
	@Column(name="description")
	private String desc;
	@Column(name="price")
	private Double price;

	public static enum PostStatusEnum
	{
		available,
		unlisted,
		sold
	}

	@Enumerated(EnumType.STRING)
	//Hibernate 6
	//otherwise we'd need to use native query
	@JdbcType(PostgreSQLEnumJdbcType.class)
	@Column(name="status")
	private PostStatusEnum status;

	public PostEntity() {}

	public PostEntity(PostEntity postEntity) {
		this.post_id = postEntity.post_id;
		this.last_edit = postEntity.last_edit;
		this.created_at = postEntity.created_at;
		this.seller_id = postEntity.seller_id;
		this.desc = postEntity.desc;
		this.price = postEntity.price;
		this.status = postEntity.status;
	}

	public PostEntity(Integer post_id, Instant last_edit, Instant created_at, Integer seller_id, String desc, Double price, PostStatusEnum status) {
		this.post_id = post_id;
		this.last_edit = last_edit;
		this.created_at = created_at;
		this.seller_id = seller_id;
		this.desc = desc;
		this.price = price;
		this.status = status;
	}

	public Integer getPost_id() {return post_id;}
	public void setPost_id(Integer post_id) {this.post_id = post_id;}
	public Instant getLast_edit() {return last_edit;}
	public void setLast_edit(Instant last_edit) {this.last_edit = last_edit;}
	public Instant getCreated_at() {return created_at;}
	public void setCreated_at(Instant created_at) {this.created_at = created_at;}
	public Integer getSeller_id() {return seller_id;}
	public void setSeller_id(Integer seller_id) {this.seller_id = seller_id;}
	public String getDesc() {return desc;}
	public void setDesc(String desc) {this.desc = desc;}
	public Double getPrice() {return price;}
	public void setPrice(Double price) {this.price = price;}
	public PostStatusEnum getStatus() {return status;}
	public void setStatus(PostStatusEnum status) {this.status = status;}

	@Override
	public boolean equals(Object object) {
		if (object == null || getClass() != object.getClass()) return false;
		if (!super.equals(object)) return false;

		PostEntity that = (PostEntity) object;
		return java.util.Objects.equals(post_id, that.post_id) && java.util.Objects.equals(last_edit, that.last_edit) && java.util.Objects.equals(created_at, that.created_at) && java.util.Objects.equals(seller_id, that.seller_id) && java.util.Objects.equals(desc, that.desc) && java.util.Objects.equals(price, that.price) && status == that.status;
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + Objects.hashCode(post_id);
		result = 31 * result + Objects.hashCode(last_edit);
		result = 31 * result + Objects.hashCode(created_at);
		result = 31 * result + Objects.hashCode(seller_id);
		result = 31 * result + Objects.hashCode(desc);
		result = 31 * result + Objects.hashCode(price);
		result = 31 * result + Objects.hashCode(status);
		return result;
	}

	@java.lang.Override
	public java.lang.String toString() {
		return "PostEntity{" +
				"post_id=" + post_id +
				", last_edit=" + last_edit +
				", created_at=" + created_at +
				", seller_id=" + seller_id +
				", desc='" + desc + '\'' +
				", price=" + price +
				", status=" + status +
				'}';
	}
}

/*
    id SERIAL PRIMARY KEY,
    seller_id INT REFERENCES users(id) ON DELETE CASCADE,
    description TEXT NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    status post_status_enum DEFAULT 'unlisted',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_edit_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
 */
>>>>>>> 86cf0c65615775d533fa1fd05d2e82332ac5d6fb
