
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

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="messages")
public class MessageEntity {
	@Column(name = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer messageId;

	private Integer senderId;
	private Integer receiverId;
	//private Integer postId;
	@Column(name="message_text")
	private String message;
	private Instant sentAt;
	private Instant readAt;

	public MessageEntity() {}

	public MessageEntity(MessageEntity messageEntity) {
		this.messageId = messageEntity.messageId;
		this.senderId = messageEntity.senderId;
		this.receiverId = messageEntity.receiverId;
		this.message = messageEntity.message;
		this.sentAt = messageEntity.sentAt;
		this.readAt = messageEntity.readAt;
	}

	public MessageEntity(Integer message_id, Integer sender_id, Integer receiver_id, String message, Instant sent_at, Instant read_at) {
		this.messageId = message_id;
		this.senderId = sender_id;
		this.receiverId = receiver_id;
		this.message = message;
		this.sentAt = sent_at;
		this.readAt = read_at;
	}

	public Integer getMessageId() {return messageId;}
	public void setMessageId(Integer messageId) {this.messageId = messageId;}
	public Integer getSenderId() {return senderId;}
	public void setSenderId(Integer senderId) {this.senderId = senderId;}
	public Integer getReceiverId() {return receiverId;}
	public void setReceiverId(Integer receiverId) {this.receiverId = receiverId;}
/*  public Integer getPostId() {return postId;}
	public void setPostId(Integer postId) {this.postId = postId;}*/
	public String getMessage() {return message;}
	public void setMessage(String message) {this.message = message;}
	public Instant getSentAt() {return sentAt;}
	public void setSentAt(Instant sentAt) {this.sentAt = sentAt;}
	public Instant getReadAt() {return readAt;}
	public void setReadAt(Instant readAt) {this.readAt = readAt;}

	@Override
	public boolean equals(Object object) {
		if (object == null || getClass() != object.getClass()) return false;
		if (!super.equals(object)) return false;
		MessageEntity that = (MessageEntity) object;
		return java.util.Objects.equals(messageId, that.messageId) && java.util.Objects.equals(senderId, that.senderId) && java.util.Objects.equals(receiverId, that.receiverId) && java.util.Objects.equals(message, that.message) && java.util.Objects.equals(sentAt, that.sentAt) && java.util.Objects.equals(readAt, that.readAt);
	}

	@Override
	public int hashCode() {
		return java.util.Objects.hash(super.hashCode(), messageId, senderId, receiverId, message, sentAt, readAt);
	}

	@Override
	public java.lang.String toString() {
		return "MessageEntity{" +
				"message_id=" + messageId +
				", sender_id=" + senderId +
				", receiver_id=" + receiverId +
				", message='" + message + '\'' +
				", sent_at=" + sentAt +
				", read_at=" + readAt +
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