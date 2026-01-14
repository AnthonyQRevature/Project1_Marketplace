package project.Repository.Entities;

import java.sql.Date;
import java.util.Objects;

import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.type.PostgreSQLEnumJdbcType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
    public static enum UserRole
    {
        user,
        admin,
        super_user //super is a keyword
    }

    @Column(name="id")
    @Id
    /*
     * relies on a database's auto-increment feature
     */
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    @Column(name="username")
    private String username;
    private String email;
    private String passwordHash;
    @Enumerated(EnumType.STRING)
    //Hibernate 6
    //otherwise we'd need to use native query
    @JdbcType(PostgreSQLEnumJdbcType.class)
    private UserRole role;
    private Boolean verifiedSeller;

    public UserEntity() {
    }

    public UserEntity(UserEntity o)
    {
<<<<<<< HEAD
        this(o.getCreatedAt(), o.email, o.passwordHash, o.role, o.id, o.username, o.verifiedSeller);
=======
        this(o.email, o.passwordHash, o.role, o.userId, o.username, o.verifiedSeller);
>>>>>>> 86cf0c65615775d533fa1fd05d2e82332ac5d6fb
    }

    public UserEntity(String email, String passwordHash, UserRole role, Integer userId, String username, Boolean verifiedSeller) {
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
        this.id = userId;
        this.username = username;
        this.verifiedSeller = verifiedSeller;
    }

    public UserEntity(Date createdAt, String email, String passwordHash, UserRole role, String username, Boolean verifiedSeller) {
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
        this.username = username;
        this.verifiedSeller = verifiedSeller;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer userId) {
        this.id = userId;
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

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
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
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.id);
        hash = 29 * hash + Objects.hashCode(this.username);
        hash = 29 * hash + Objects.hashCode(this.email);
        hash = 29 * hash + Objects.hashCode(this.passwordHash);
        hash = 29 * hash + Objects.hashCode(this.role);
        hash = 29 * hash + Objects.hashCode(this.verifiedSeller);
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
        final UserEntity other = (UserEntity) obj;
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.passwordHash, other.passwordHash)) {
            return false;
        }
        if (!Objects.equals(this.role, other.role)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.verifiedSeller, other.verifiedSeller)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("userEntity{");
        sb.append("userId=").append(id);
        sb.append(", username=").append(username);
        sb.append(", email=").append(email);
        sb.append(", passwordHash=").append(passwordHash);
        sb.append(", role=").append(role);
        sb.append(", verifiedSeller=").append(verifiedSeller);
        sb.append('}');
        return sb.toString();
    }
}
