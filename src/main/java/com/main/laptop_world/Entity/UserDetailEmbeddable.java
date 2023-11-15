package com.main.laptop_world.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailEmbeddable {
    @Column(name = "first_name", table = "user_detail")
    private String firstName;
    @Column(name = "last_name", table = "user_detail")
    private String lastName;
    @Column(name = "full_name", table = "user_detail")
    private String fullName;
    @Column(name = "address", table = "user_detail")
    private String address;
    @Column(name = "phone_number", table = "user_detail")
    private String phoneNumber;

}
