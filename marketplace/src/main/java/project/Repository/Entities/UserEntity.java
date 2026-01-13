package project.Repository.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/*
 * Class Modeling the users table using JPA
 */
@Entity
@Table(name="users")
public class UserEntity {

    @Column(name="id")
    @Id
    /*
     * relies on a database's auto-increment feature
     */
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer userId;
    @Column(name="username")
    private String username;
    private String email;
    private String passwordHash;
    private String role;
    private Boolean verifiedSeller;

    public UserEntity() {}

    public UserEntity(UserEntity userEntity){
        this.userId = userEntity.userId;
        this.username = userEntity.username;
        this.email = userEntity.email;
        this.passwordHash = userEntity.passwordHash;
        this.role=  userEntity.role;
        this.verifiedSeller = userEntity.verifiedSeller;
    }

    public UserEntity(Integer userId, String username, String email, String passwordHash, String role, Boolean verifiedSeller) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
        this.verifiedSeller = verifiedSeller;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Boolean getVerifiedSeller() {
        return verifiedSeller;
    }

    public void setVerifiedSeller(Boolean verifiedSeller) {
        this.verifiedSeller = verifiedSeller;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((userId == null) ? 0 : userId.hashCode());
        result = prime * result + ((username == null) ? 0 : username.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((passwordHash == null) ? 0 : passwordHash.hashCode());
        result = prime * result + ((role == null) ? 0 : role.hashCode());
        result = prime * result + ((verifiedSeller == null) ? 0 : verifiedSeller.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UserEntity other = (UserEntity) obj;
        if (userId == null) {
            if (other.userId != null)
                return false;
        } else if (!userId.equals(other.userId))
            return false;
        if (username == null) {
            if (other.username != null)
                return false;
        } else if (!username.equals(other.username))
            return false;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        if (passwordHash == null) {
            if (other.passwordHash != null)
                return false;
        } else if (!passwordHash.equals(other.passwordHash))
            return false;
        if (role == null) {
            if (other.role != null)
                return false;
        } else if (!role.equals(other.role))
            return false;
        if (verifiedSeller == null) {
            if (other.verifiedSeller != null)
                return false;
        } else if (!verifiedSeller.equals(other.verifiedSeller))
            return false;
        return true;
    }
}
