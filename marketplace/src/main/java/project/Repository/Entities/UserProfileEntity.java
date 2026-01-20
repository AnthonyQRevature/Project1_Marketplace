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
	private Integer profileId;
	@Column(name="user_id")
	private Integer user_id;
	@Column(name="pfp_url")
	private String pfp_url;
	@Column(name="bio")
	private String bio;
	@Column(name="latitude")
	private double latitude;
	@Column(name="longitude")
	private double longitude;
	@Column(name="address")
	private String address;

	public UserProfileEntity() {}

	public UserProfileEntity(UserProfileEntity userProfileEntity) {
		this.profileId = userProfileEntity.profileId;
		this.user_id = userProfileEntity.user_id;
		this.pfp_url = userProfileEntity.pfp_url;
		this.bio = userProfileEntity.bio;
		this.latitude = userProfileEntity.latitude;
		this.longitude = userProfileEntity.longitude;
		this.address = userProfileEntity.address;
	}

	public Integer getProfileId() {return profileId;}
	public void setProfileId(Integer profileId) {this.profileId = profileId;}
	public Integer getUserID() {return user_id;}
	public void setUserID(Integer user_id) {this.user_id = user_id;}
	public String getPfpUrl() {return pfp_url;}
	public void setPfpUrl(String pfp_url) {this.pfp_url = pfp_url;}
	public String getBio() {return bio;}
	public void setBio(String bio) {this.bio = bio;}
	public double getLatitude() {return latitude;}
	public void setLatitude(double latitude) {this.latitude = latitude;}
	public double getLongitude() {return longitude;}
	public void setLongitude(double longitude) {this.longitude = longitude;}
	public String getAddress() {return address;}
	public void setAddress(String address) {this.address = address;}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		UserProfileEntity that = (UserProfileEntity) o;
		return Double.compare(latitude, that.latitude) == 0 && Double.compare(longitude, that.longitude) == 0 && Objects.equals(profileId, that.profileId) && Objects.equals(user_id, that.user_id) && Objects.equals(pfp_url, that.pfp_url) && Objects.equals(bio, that.bio) && Objects.equals(address, that.address);
	}

	@Override
	public int hashCode() {
		return Objects.hash(profileId, user_id, pfp_url, bio, latitude, longitude, address);
	}

	@Override
	public String toString() {
		return "UserProfileEntity{" +
				"profileId=" + profileId +
				", user_id=" + user_id +
				", pfp_url='" + pfp_url + '\'' +
				", bio='" + bio + '\'' +
				", latitude=" + latitude +
				", longitude=" + longitude +
				", address='" + address + '\'' +
				'}';
	}
}