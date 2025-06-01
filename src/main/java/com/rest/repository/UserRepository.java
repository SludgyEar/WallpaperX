package com.rest.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.rest.entities.User;
import org.springframework.transaction.annotation.Transactional;

// This class will handle custom querys

@Repository
public interface UserRepository extends CrudRepository<User, Integer>{
    @Query("SELECT u FROM User u")
    List<User> getAllUsers();

    @Query("SELECT u FROM User u WHERE u.id = ?1")
    Optional<User> getUserById(int id);

    @Query("SELECT u FROM User u WHERE u.userName = ?1")
    Optional<User>getUserByUsername(String userName);

    @Query("SELECT u FROM User u WHERE u.email = ?1")
    Optional<User> getUserByEmail(String email);

    @Modifying
    @Transactional
    @Query(
        value = "INSERT INTO User (user_name, email, password, rol, state, registered_date) VALUES (?1, ?2, ?3, ?4, ?5, ?6)",
        nativeQuery = true)
    void addUser(String userName, String email, String password, String rol, int state, String registeredDate);

    // The update method is not needed in this case, as the User entity is managed by Spring Data JPA
    // Altough, you can define a custom update method if needed

    @Modifying
    @Transactional
    @Query("DELETE FROM User u WHERE u.id = ?1")
    void deleteUserById(int id);

    @Query("SELECT u FROM User u WHERE u.userName = ?1 AND u.password = ?2")
    Optional<User> getUserAuth(String userName, String password);
}
