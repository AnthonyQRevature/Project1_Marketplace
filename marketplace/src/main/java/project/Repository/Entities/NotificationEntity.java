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
@Table(name="notifications")
public class NotificationEntity {
	@Column(name="id")
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer notification_id;
	@Column(name="user_id")
	private Integer user_id;
	@Column(name="content")
	private  String text;
	enum Notification_Type
	{
		MESSAGE_RECEIVED,
		SYSTEM,
		ADMIN_ACTION;
	}
	@Column(name="type")
	Notification_Type type;
	@Column(name="read")
	Boolean read;
	@Column(name="created_at")
	Instant created_at;

	public NotificationEntity() {}

	public NotificationEntity(NotificationEntity notificationEntity) {
		this.notification_id = notificationEntity.notification_id;
		this.user_id = notificationEntity.user_id;
		this.text = notificationEntity.text;
		this.type = notificationEntity.type;
		this.read = notificationEntity.read;
		this.created_at = notificationEntity.created_at;
	}

	public NotificationEntity(Integer notification_id, Integer user_id, String text, Notification_Type type, Boolean read, Instant created_at) {
		this.notification_id = notification_id;
		this.user_id = user_id;
		this.text = text;
		this.type = type;
		this.read = read;
		this.created_at = created_at;
	}

		//getters and setters
	public Integer getNotification_id() {return notification_id;}
	public void setNotification_id(Integer notification_id) {this.notification_id = notification_id;}
	public Integer getUser_id() {return user_id;}
	public void setUser_id(Integer user_id) {this.user_id = user_id;}
	public String getText() {return text;}
	public void setText(String text) {this.text = text;}
	public Notification_Type getType() {return type;}
	public void setType(Notification_Type type) {this.type = type;}
	public Boolean getRead() {return read;}
	public void setRead(Boolean read) {this.read = read;}
	public Instant getCreated_at() {return created_at;}
	public void setCreated_at(Instant created_at) {this.created_at = created_at;}


	//equals, hash, toString
	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		NotificationEntity that = (NotificationEntity) o;
		return Objects.equals(notification_id, that.notification_id) && Objects.equals(user_id, that.user_id) && Objects.equals(text, that.text) && type == that.type && Objects.equals(read, that.read) && Objects.equals(created_at, that.created_at);
	}

	@Override
	public int hashCode() {
		return Objects.hash(notification_id, user_id, text, type, read, created_at);
	}

	@Override
	public String toString() {
		return "NotificationEntity{" +
				"notification_id=" + notification_id +
				", user_id=" + user_id +
				", text='" + text + '\'' +
				", type=" + type +
				", read=" + read +
				", created_at=" + created_at +
				'}';
	}
}
