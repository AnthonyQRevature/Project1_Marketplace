package project.Repository.Entities;

import java.util.Objects;

import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.type.PostgreSQLEnumJdbcType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

/*
 * Class Modeling the users table using JPA
 */
@Entity
@Table(name="users")
public class UserEntity {

    public UserProfileEntity getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfileEntity userProfile) {
        this.userProfile = userProfile;
    }
    public static enum UserRole
    {
        user(1),
        admin(2),
        super_user(3); //super is a keyword

        public final int value;

        UserRole(int val)
        {
            this.value = val;
        }
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

    @OneToOne(mappedBy="userEntity")
    private UserProfileEntity userProfile;

    public UserEntity() {
    }

    public UserEntity(UserEntity o)
    {
        this(o.email, o.passwordHash, o.role, o.id, o.username, o.verifiedSeller);
    }

    public UserEntity(String email, String passwordHash, UserRole role, Integer user_id, String username, Boolean verifiedSeller) {
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
        this.id = user_id;
        this.username = username;
        this.verifiedSeller = verifiedSeller;
    }

    public UserEntity(String email, String passwordHash, UserRole role, String username, Boolean verifiedSeller) {
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
        this.username = username;
        this.verifiedSeller = verifiedSeller;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer user_id) {
        this.id = user_id;
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
        sb.append("user_id=").append(id);
        sb.append(", username=").append(username);
        sb.append(", email=").append(email);
        sb.append(", passwordHash=").append(passwordHash);
        sb.append(", role=").append(role);
        sb.append(", verifiedSeller=").append(verifiedSeller);
        sb.append('}');
        return sb.toString();
    }
}
