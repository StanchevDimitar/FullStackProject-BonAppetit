package com.bonappetit.model.DTO;

import antlr.Token;

public class UserJWTDto {

    private Long id;
    private String username;

    private String email;
    private String  token;

    public UserJWTDto() {
    }

    public String getToken() {
        return token;
    }

    public UserJWTDto setToken(String token) {
        this.token = token;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserJWTDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public Long getId() {
        return id;
    }

    public UserJWTDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserJWTDto setUsername(String username) {
        this.username = username;
        return this;
    }

    @Override
    public String toString() {
        return "UserJWTDto{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
