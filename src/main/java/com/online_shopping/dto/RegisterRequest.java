package com.online_shopping.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;



public class RegisterRequest {

    @NotBlank(message = "用户名不能为空")
    @Size(min = 4, max = 20, message = "用户名长度需在4到20个字符之间")
    private String username;

    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;

    @NotBlank(message = "密码不能为空")
    @Size(min = 3, message = "密码长度至少为3位")
    private String password;

    public @NotBlank(message = "用户名不能为空") @Size(min = 4, max = 20, message = "用户名长度需在4到20个字符之间") String getUsername() {
        return username;
    }

    public void setUsername(@NotBlank(message = "用户名不能为空") @Size(min = 4, max = 20, message = "用户名长度需在4到20个字符之间") String username) {
        this.username = username;
    }

    public @NotBlank(message = "邮箱不能为空") @Email(message = "邮箱格式不正确") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "邮箱不能为空") @Email(message = "邮箱格式不正确") String email) {
        this.email = email;
    }

    public @NotBlank(message = "密码不能为空") @Size(min = 3, message = "密码长度至少为3位") String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank(message = "密码不能为空") @Size(min = 3, message = "密码长度至少为3位") String password) {
        this.password = password;
    }
}
