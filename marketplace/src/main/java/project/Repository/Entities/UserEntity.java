package project.Repository.Entities;

import java.sql.Date;
import java.util.Objects;

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
}
