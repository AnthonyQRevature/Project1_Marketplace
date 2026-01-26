package project.controller.response;

public class ProfileBriefResponse {
    int id;
    String username;
    String pfp_encoded;

    public ProfileBriefResponse() {
    }

    public ProfileBriefResponse(int id, String username, String pfp_encoded) {
        this.id = id;
        this.username = username;
        this.pfp_encoded = pfp_encoded;
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

    public String getPfp_encoded() {
        return pfp_encoded;
    }

    public void setPfp_encoded(String pfp_encoded) {
        this.pfp_encoded = pfp_encoded;
    }

}
