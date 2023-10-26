package com.main.laptop_world.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "users")
@SecondaryTable(name = "user_detail", pkJoinColumns = @PrimaryKeyJoinColumn(name = "user_detail_id"))
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private boolean isActive = true;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"))
    private List<Role> roles = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Order> orders;

    @OneToMany(mappedBy = "user")
    private List<Cart> carts;

    @OneToMany(mappedBy = "user")
    private List<Transaction> transactions;

    @Embedded
    private UserDetailEmbeddable userDetailEmbeddable;
}

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
class UserDetailEmbeddable {
    @Column(name = "first_name", table = "user_detail")
    private String firstName;
    @Column(name = "last_name", table = "user_detail")
    private String lastName;
    @Column(name = "full_name", table = "user_detail")
    private String fullName;
    @Column(name = "email", table = "user_detail")
    private String email;
    @Column(name = "address", table = "user_detail")
    private String address;
    @Column(name = "phone_number", table = "user_detail")
    private String phoneNumber;

}
