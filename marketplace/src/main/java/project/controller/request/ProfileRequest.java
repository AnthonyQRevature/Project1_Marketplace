package project.controller.request;

public class ProfileRequest {
    // TODO: add address?
    String pfp_encoded;
    String bio;
    Double latitude;
    Double longitude;
    Double distance;

    public ProfileRequest(String bio, Double latitude, Double longitude, String pfp_encoded) {
        this.bio = bio;
        this.latitude = latitude;
        this.longitude = longitude;
        this.pfp_encoded = pfp_encoded;
        // TODO: Set this up
        this.distance = 0.0;
    }

    public String getPfp_encoded() {
        return pfp_encoded;
    }
    public void setPfp_encoded(String pfp_encoded) {
        this.pfp_encoded = pfp_encoded;
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

    public Double getDistance() {
        return distance;
    }
    public void setDistance(Double distance) {
        this.distance = distance;
    }
}