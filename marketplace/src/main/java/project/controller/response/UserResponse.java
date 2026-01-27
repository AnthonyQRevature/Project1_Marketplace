package project.controller.response;

public class UserResponse {
    int id;
    String username;
    String email;
    Boolean verified_seller;
    ProfileResponse profile;

    public UserResponse(String email, int id, ProfileResponse profileResponse, String username, Boolean verified_seller) {
        this.email = email;
        this.id = id;
        this.profile = profileResponse;
        this.username = username;
        this.verified_seller = verified_seller;
    }
    public UserResponse() {
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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

    public Boolean isVerified_seller() {
        return verified_seller;
    }
    public void setVerified_seller(Boolean verified_seller) {
        this.verified_seller = verified_seller;
    }

    public ProfileResponse getProfile() {
        return profile;
    }
    public void setProfile(ProfileResponse profileResponse) {
        this.profile = profileResponse;
    }
}
