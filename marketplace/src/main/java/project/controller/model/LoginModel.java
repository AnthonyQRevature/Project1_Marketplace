package project.controller.model;

/*
 * JSON serializable Object representing a login or register attempt
 * used by the controller layer
 */
public class LoginModel {
    //an object is Jackson serializable if it has getters and setters
    Integer user_id;
    String username;
    String token;

    public LoginModel(Integer user_id, String username, String token) {
        this.user_id = user_id;
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
        return user_id;
    }

    public void setUserId(Integer user_id) {
        this.user_id = user_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}