package project.controller.model;

/*
 * JSON serializable Object representing a login or register attempt
 * used by the controller layer
 */
public class LoginModel {
    //an object is Jackson serializable if it has getters and setters
    Integer userId;
    String username;
    String token;

    public LoginModel(Integer userId, String username, String token) {
        this.userId = userId;
        this.username = username;
        this.token = token;
    }

    public LoginModel() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}