package project.controller.response;

public class UserResponse {
    int id;
    String username;
    String email;
    boolean verified_seller;
    ProfileResponse profileResponse;

    public UserResponse(String email, int id, ProfileResponse profileResponse, String username, boolean verified_seller) {
        this.email = email;
        this.id = id;
        this.profileResponse = profileResponse;
        this.username = username;
        this.verified_seller = verified_seller;
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

    public boolean isVerified_seller() {
        return verified_seller;
    }
    public void setVerified_seller(boolean verified_seller) {
        this.verified_seller = verified_seller;
    }

    public ProfileResponse getProfileResponse() {
        return profileResponse;
    }
    public void setProfileResponse(ProfileResponse profileResponse) {
        this.profileResponse = profileResponse;
    }
}
