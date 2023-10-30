package com.main.laptop_world.Entity.DTO;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;

    @NotEmpty(message = "Username not null!")
    private String username;

    @NotEmpty(message = "Password not null!")
    private String password;

    @NotEmpty(message = "Email not null!")
    private String email;

    @NotEmpty(message = "Confirm password not null!")
    private String confirmPassword;
}
