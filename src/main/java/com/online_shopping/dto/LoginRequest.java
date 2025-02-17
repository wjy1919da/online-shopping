package com.online_shopping.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class LoginRequest {

    @NotNull
    @Size(min = 3, max = 255)
    private String username;

    @NotNull
    @Size(min = 3, max = 255)
    private String password;

    public @NotNull @Size(min = 3, max = 255) String getUsername() {
        return username;
    }

    public void setUsername(@NotNull @Size(min = 3, max = 255) String username) {
        this.username = username;
    }

    public @NotNull @Size(min = 3, max = 255) String getPassword() {
        return password;
    }

    public void setPassword(@NotNull @Size(min = 3, max = 255) String password) {
        this.password = password;
    }
}

