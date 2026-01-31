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
	private Integer userID;
	@Column(name="pfp_encoded")
	private String pfpEncoded;
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
		this.userID = userProfileEntity.userID;
		this.pfpEncoded = userProfileEntity.pfpEncoded;
		this.bio = userProfileEntity.bio;
		this.latitude = userProfileEntity.latitude;
		this.longitude = userProfileEntity.longitude;
		this.address = userProfileEntity.address;
	}

	public Integer getId() {return id;}
	public void setId(Integer id) {this.id = id;}
	public Integer getUserID() {return userID;}
	public void setUserID(Integer user_id) {this.userID = user_id;}
	public String getPfpEncoded() {return pfpEncoded;}
	public void setPfpEncoded(String pfp_encoded) {this.pfpEncoded = pfp_encoded;}
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
		return Double.compare(latitude, that.latitude) == 0 && Double.compare(longitude, that.longitude) == 0 && Objects.equals(id, that.id) && Objects.equals(userID, that.userID) && Objects.equals(pfpEncoded, that.pfpEncoded) && Objects.equals(bio, that.bio) && Objects.equals(address, that.address);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, userID, pfpEncoded, bio, latitude, longitude, address);
	}

	@Override
	public String toString() {
		return "UserProfileEntity{" +
				"id=" + id +
				", user_id=" + userID +
				", pfp_encoded='" + pfpEncoded + '\'' +
				", bio='" + bio + '\'' +
				", latitude=" + latitude +
				", longitude=" + longitude +
				", address='" + address + '\'' +
				'}';
	}
}