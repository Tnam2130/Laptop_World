package com.main.laptop_world.Repository;


import com.main.laptop_world.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);
    @Query("SELECT c FROM User c WHERE c.username = :username")
    Optional<User> findByName(String username);
    User findByEmail(String email);
    User findByUsernameAndProvider(String username, String provider);
}
