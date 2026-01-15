package project.controller.response;

public class LoginResponse {
    Integer id;
    String username;
    String encryptedToken;

    public LoginResponse(Integer id, String username, String encryptedToken) {
        this.encryptedToken = encryptedToken;
        this.id = id;
        this.username = username;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEncryptedToken() {
        return encryptedToken;
    }

    public void setEncryptedToken(String encryptedToken) {
        this.encryptedToken = encryptedToken;
    }
}
