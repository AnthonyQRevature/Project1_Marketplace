package project.Repository.Entities;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="user_profile")
public class UserProfileEntity {
	@Column(name="id")
	@Id
	/*
	 * relies on a database's auto-increment feature
	 */
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@Column(name="user_id")
	private Integer user_id;
	@Column(name="pfp_encoded")
	private String pfp_encoded;
	@Column(name="bio")
	private String bio;
	@Column(name="latitude")
	private Double latitude;
	@Column(name="longitude")
	private Double longitude;
	@Column(name="address")
	private String address;

	public UserProfileEntity() {}

	public UserProfileEntity(UserProfileEntity userProfileEntity) {
		this.id = userProfileEntity.id;
		this.user_id = userProfileEntity.user_id;
		this.pfp_encoded = userProfileEntity.pfp_encoded;
		this.bio = userProfileEntity.bio;
		this.latitude = userProfileEntity.latitude;
		this.longitude = userProfileEntity.longitude;
		this.address = userProfileEntity.address;
	}

	public Integer getId() {return id;}
	public void setId(Integer id) {this.id = id;}
	public Integer getUserID() {return user_id;}
	public void setUserID(Integer user_id) {this.user_id = user_id;}
	public String getPfp_encoded() {return pfp_encoded;}
	public void setPfp_encoded(String pfp_encoded) {this.pfp_encoded = pfp_encoded;}
	public String getBio() {return bio;}
	public void setBio(String bio) {this.bio = bio;}
	public Double getLatitude() {return latitude;}
	public void setLatitude(Double latitude) {this.latitude = latitude;}
	public Double getLongitude() {return longitude;}
	public void setLongitude(Double longitude) {this.longitude = longitude;}
	public String getAddress() {return address;}
	public void setAddress(String address) {this.address = address;}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		UserProfileEntity that = (UserProfileEntity) o;
		return Double.compare(latitude, that.latitude) == 0 && Double.compare(longitude, that.longitude) == 0 && Objects.equals(id, that.id) && Objects.equals(user_id, that.user_id) && Objects.equals(pfp_encoded, that.pfp_encoded) && Objects.equals(bio, that.bio) && Objects.equals(address, that.address);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, user_id, pfp_encoded, bio, latitude, longitude, address);
	}

	@Override
	public String toString() {
		return "UserProfileEntity{" +
				"id=" + id +
				", user_id=" + user_id +
				", pfp_encoded='" + pfp_encoded + '\'' +
				", bio='" + bio + '\'' +
				", latitude=" + latitude +
				", longitude=" + longitude +
				", address='" + address + '\'' +
				'}';
	}
}