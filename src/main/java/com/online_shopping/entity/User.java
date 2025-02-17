package com.online_shopping.entity;



import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(name = "user")
public class User {
    @Id
    @Column(name = "user_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 255)
    @NotNull
    @Column(name = "username", nullable = false)
    private String username;

    @Size(max = 255)
    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @Size(min = 3, message = "密码长度至少为6位")
    @NotNull
    @Column(name = "password", nullable = false)
    private String password;

    @NotNull
    @Column(name = "role", nullable = false)
    private Integer role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @Size(max = 255) @NotNull String getUsername() {
        return username;
    }

    public void setUsername(@Size(max = 255) @NotNull String username) {
        this.username = username;
    }

    public @Size(max = 255) @NotNull String getEmail() {
        return email;
    }

    public void setEmail(@Size(max = 255) @NotNull String email) {
        this.email = email;
    }

    public @Size(max = 255) @NotNull String getPassword() {
        return password;
    }

    public void setPassword(@Size(max = 255) @NotNull String password) {
        this.password = password;
    }

    public @NotNull Integer getRole() {
        return role;
    }

    public void setRole(@NotNull Integer role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}