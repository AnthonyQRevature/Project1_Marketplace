package project.controller.response;

public class ProfileResponse {
    String pfp_url;
    String bio;
    Integer latitude;
    Integer longitude;

    public ProfileResponse(String bio, Integer latitude, Integer longitude, String pfp_url) {
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

    public Integer getLatitude() {
        return latitude;
    }
    public void setLatitude(Integer latitude) {
        this.latitude = latitude;
    }
    
    public Integer getLongitude() {
        return longitude;
    }
    public void setLongitude(Integer longitude) {
        this.longitude = longitude;
    }
}