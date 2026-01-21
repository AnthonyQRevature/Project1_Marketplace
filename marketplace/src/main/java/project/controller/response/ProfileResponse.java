package project.controller.response;

public class ProfileResponse {
    String pfp_url;
    String bio;
    Double latitude;
    Double longitude;

    public ProfileResponse(String bio, Double latitude, Double longitude, String pfp_url) {
        this.bio = bio;
        this.latitude = latitude;
        this.longitude = longitude;
        this.pfp_url = pfp_url;
    }

    public String getPfp_url() {
        return pfp_url;
    }
    public void setPfp_url(String pfp_url) {
        this.pfp_url = pfp_url;
    }

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