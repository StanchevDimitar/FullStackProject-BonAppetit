package com.bonappetit.model.DTO;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserRegisterDto {

    @NotNull
    @Size(min = 3, max = 20, message ="Username must be between 3 and 20 symbols")
    private String username;
    @Email( message ="Invalid email")
    @NotNull
    private String email;
    @NotNull
    @Size(min = 3, max = 20, message ="Password must be between 3 and 20 symbols")
    private String password;
    @NotNull
    @Size(min = 3, max = 20, message ="Password must be between 3 and 20 symbols")
    private String confirmPassword;

    public UserRegisterDto() {
    }

    public String getUsername() {
        return username;
    }

    public UserRegisterDto setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserRegisterDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserRegisterDto setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public UserRegisterDto setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }
}
