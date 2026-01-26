package project.Repository.Entities;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;


@Entity
@Table(name="conversations")
@IdClass(ConversationEntity.ConversationId.class)
public class ConversationEntity {
    
    @Id
    Integer sender;
    @Id
    Integer reciever;
    String username;
    String pfpEncoded;

    public ConversationEntity() {
    }

    public Integer getSender() {
        return sender;
    }

    public void setSender(Integer sender) {
        this.sender = sender;
    }

    public Integer getReciever() {
        return reciever;
    }

    public void setReciever(Integer reciever) {
        this.reciever = reciever;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPfpEncoded() {
        return pfpEncoded;
    }

    public void setPfpEncoded(String pfpEncoded) {
        this.pfpEncoded = pfpEncoded;
    }
    public static class ConversationId implements Serializable
    {
        private Integer sender;
        private Integer reciever;

        public ConversationId() {
        }

        public Integer getSender() {
            return sender;
        }

        public void setSender(Integer sender) {
            this.sender = sender;
        }

        public Integer getReciever() {
            return reciever;
        }

        public void setReciever(Integer reciever) {
            this.reciever = reciever;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 53 * hash + Objects.hashCode(this.sender);
            hash = 53 * hash + Objects.hashCode(this.reciever);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final ConversationId other = (ConversationId) obj;
            if (!Objects.equals(this.sender, other.sender)) {
                return false;
            }
            return Objects.equals(this.reciever, other.reciever);
        }
    }
}
