package project.Repository.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class UserDistanceEntity {
    @Id
    int user_id;
    String username;
    String pfp_encoded;
    double distance;

    public UserDistanceEntity(double distance, String pfp_encoded, int user_id, String username) {
        this.distance = distance;
        this.pfp_encoded = pfp_encoded;
        this.user_id = user_id;
        this.username = username;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPfp_encoded() {
        return pfp_encoded;
    }

    public void setPfp_encoded(String pfp_encoded) {
        this.pfp_encoded = pfp_encoded;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("UserDistanceEntity{");
        sb.append("user_id=").append(user_id);
        sb.append(", username=").append(username);
        sb.append(", pfp_encoded=").append(pfp_encoded);
        sb.append(", distance=").append(distance);
        sb.append('}');
        return sb.toString();
    }
}
