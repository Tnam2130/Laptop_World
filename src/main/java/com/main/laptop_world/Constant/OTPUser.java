package com.main.laptop_world.Constant;

import com.main.laptop_world.Entity.User;

import java.time.LocalDateTime;

public record OTPUser(User users, LocalDateTime expirationTime) {

}
