package com.rest.persistence;

/*
* In this interface, you can define methods for user-related database operations
*/

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import com.rest.entities.User;

@Repository
public interface IUserDAO {
    
    List<User> getAllUsers();

    Optional<User> getUserById(int id);

    Optional<User> getUserByUsername(String userName);

    Optional<User> getUserByEmail(String email);

    void addUser(String userName, String email, String password, String rol, int state, String registeredDate);

    void updateUser(User user);

    void deleteUserById(int id);

    Optional<User> getUserAuth(String userName, String password);

    List<User> getUserByRol(String rol);

    List<User> getUserByState(int state);
}
