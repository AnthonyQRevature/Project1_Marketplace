
/*
    id SERIAL PRIMARY KEY,
    sender_id INT REFERENCES users(id) ON DELETE CASCADE,
    receiver_id INT REFERENCES users(id) ON DELETE CASCADE,
    post_id INT REFERENCES post(id) ON DELETE SET NULL,
    message_text TEXT NOT NULL,
    sent_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    read_at TIMESTAMP
 */

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
@Table(name="messages")
public class MessageEntity {
	@Column(name = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer message_id;

	private Integer sender_id;
	private Integer reciever_id;
	private Integer post_id;
	private String message;
	private Instant sent_at;
	private Instant read_at;

	public MessageEntity() {}

	public MessageEntity(MessageEntity messageEntity) {
		this.message_id = messageEntity.message_id;
		this.sender_id = messageEntity.sender_id;
		this.reciever_id = messageEntity.reciever_id;
		this.post_id = messageEntity.post_id;
		this.message = messageEntity.message;
		this.sent_at = messageEntity.sent_at;
		this.read_at = messageEntity.read_at;
	}

	public MessageEntity(Integer message_id, Integer sender_id, Integer reciever_id, Integer post_id, String message, Instant sent_at, Instant read_at) {
		this.message_id = message_id;
		this.sender_id = sender_id;
		this.reciever_id = reciever_id;
		this.post_id = post_id;
		this.message = message;
		this.sent_at = sent_at;
		this.read_at = read_at;
	}

	public Integer getMessage_id() {return message_id;}
	public void setMessage_id(Integer message_id) {this.message_id = message_id;}
	public Integer getSender_id() {return sender_id;}
	public void setSender_id(Integer sender_id) {this.sender_id = sender_id;}
	public Integer getReciever_id() {return reciever_id;}
	public void setReciever_id(Integer reciever_id) {this.reciever_id = reciever_id;}
	public Integer getPost_id() {return post_id;}
	public void setPost_id(Integer post_id) {this.post_id = post_id;}
	public String getMessage() {return message;}
	public void setMessage(String message) {this.message = message;}
	public Instant getSent_at() {return sent_at;}
	public void setSent_at(Instant sent_at) {this.sent_at = sent_at;}
	public Instant getRead_at() {return read_at;}
	public void setRead_at(Instant read_at) {this.read_at = read_at;}

	public boolean equals(Object object) {
		if (object == null || getClass() != object.getClass()) return false;
		if (!super.equals(object)) return false;
		MessageEntity that = (MessageEntity) object;
		return java.util.Objects.equals(message_id, that.message_id) && java.util.Objects.equals(sender_id, that.sender_id) && java.util.Objects.equals(reciever_id, that.reciever_id) && java.util.Objects.equals(post_id, that.post_id) && java.util.Objects.equals(message, that.message) && java.util.Objects.equals(sent_at, that.sent_at) && java.util.Objects.equals(read_at, that.read_at);
	}

	public int hashCode() {
		return java.util.Objects.hash(super.hashCode(), message_id, sender_id, reciever_id, post_id, message, sent_at, read_at);
	}

	@java.lang.Override
	public java.lang.String toString() {
		return "MessageEntity{" +
				"message_id=" + message_id +
				", sender_id=" + sender_id +
				", reciever_id=" + reciever_id +
				", post_id=" + post_id +
				", message='" + message + '\'' +
				", sent_at=" + sent_at +
				", read_at=" + read_at +
				'}';
	}
}

/*
    id SERIAL PRIMARY KEY,
    sender_id INT REFERENCES users(id) ON DELETE CASCADE,
    receiver_id INT REFERENCES users(id) ON DELETE CASCADE,
    post_id INT REFERENCES post(id) ON DELETE SET NULL,
    message_text TEXT NOT NULL,
    sent_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    read_at TIMESTAMP
 */