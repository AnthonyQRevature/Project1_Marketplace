package project.controller.request;

public class UserUpdateRequest {
    String email;
    ProfileRequest profileRequest;

    public UserUpdateRequest(String email, ProfileRequest profileRequest) {
        this.email = email;
        this.profileRequest = profileRequest;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public ProfileRequest getProfileRequest() {
        return profileRequest;
    }
    public void setProfileRequest(ProfileRequest profileRequest) {
        this.profileRequest = profileRequest;
    }
}
