package project.controller.request;

public class UserUpdateRequest {
    public static class Profile
    {
        String bio;
        Double latitude;
        Double longitude;

        public String getBio() {
            return bio;
        }

        public void setBio(String bio) {
            this.bio = bio;
        }

        public Double getLatitude() {
            return latitude;
        }

        public void setLatitude(Double latitude) {
            this.latitude = latitude;
        }

        public Double getLongitude() {
            return longitude;
        }

        public void setLongitude(Double longitude) {
            this.longitude = longitude;
        }
    }
    String email;
    Profile profileRequest;

    public UserUpdateRequest(String email, Profile profileRequest) {
        this.email = email;
        this.profileRequest = profileRequest;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public Profile getProfileRequest() {
        return profileRequest;
    }
    public void setProfileRequest(Profile profileRequest) {
        this.profileRequest = profileRequest;
    }
}
