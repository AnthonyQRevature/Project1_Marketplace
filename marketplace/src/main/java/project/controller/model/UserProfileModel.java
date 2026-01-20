package project.controller.model;

public class UserProfileModel {
    Integer user_id;
	String pfp_url;
	String bio;
	double latitude;
	double longitude;
	String address;

    public UserProfileModel(Integer user_id, String pfp_url, String bio, double latitude, double longitude, String address) {
        this.user_id = user_id;
        this.pfp_url = pfp_url;
        this.bio = bio;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
    }
    public UserProfileModel(){
    }

    public Integer getUserID() {
        return user_id;
    }
    public void setUserID(Integer user_id) {
        this.user_id = user_id;
    }

    public String getPfpUrl() {
        return pfp_url;
    }
    public void setPfpUrl(String pfp_url) {
        this.pfp_url = pfp_url;
    }

    public String getBio() {
        return bio;
    }
    public void setBio(String bio) {
        this.bio = bio;
    }

    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((user_id == null) ? 0 : user_id.hashCode());
        result = prime * result + ((pfp_url == null) ? 0 : pfp_url.hashCode());
        result = prime * result + ((bio == null) ? 0 : bio.hashCode());
        long temp;
        temp = Double.doubleToLongBits(latitude);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(longitude);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + ((address == null) ? 0 : address.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UserProfileModel other = (UserProfileModel) obj;
        if (user_id == null) {
            if (other.user_id != null)
                return false;
        } else if (!user_id.equals(other.user_id))
            return false;
        if (pfp_url == null) {
            if (other.pfp_url != null)
                return false;
        } else if (!pfp_url.equals(other.pfp_url))
            return false;
        if (bio == null) {
            if (other.bio != null)
                return false;
        } else if (!bio.equals(other.bio))
            return false;
        if (Double.doubleToLongBits(latitude) != Double.doubleToLongBits(other.latitude))
            return false;
        if (Double.doubleToLongBits(longitude) != Double.doubleToLongBits(other.longitude))
            return false;
        if (address == null) {
            if (other.address != null)
                return false;
        } else if (!address.equals(other.address))
            return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("UserModel{");
        sb.append("user_id=").append(user_id);
        sb.append(", pfp_url=").append(pfp_url);
        sb.append(", bio=").append(bio);
        sb.append(", latitude=").append(latitude);
        sb.append(", longitude=").append(longitude);
        sb.append(", address=").append(address);
        sb.append('}');
        return sb.toString();
    }
}
