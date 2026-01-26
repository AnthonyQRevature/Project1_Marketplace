package project.controller.response;

import project.Repository.Entities.UserEntity.UserRole;

public class LoginResponse {
    Integer id;
    Integer role;
    String username;
    String encryptedToken;

    public LoginResponse(Integer id, UserRole role, String username, String encryptedToken) {
        this.encryptedToken = encryptedToken;
        this.id = id;
        this.username = username;
        this.role = role.value;
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

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }
}
