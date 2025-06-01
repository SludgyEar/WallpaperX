package com.rest.service;

import java.util.List;
import java.util.Optional;

import com.rest.entities.User;

public interface IUserService {
    List<User> getAllUsers();

    Optional<User> getUserById(int id);

    Optional<User> getUserByUsername(String userName);

    Optional<User> getUserByEmail(String email);

    void addUser(String userName, String email, String password, String rol, int state, String registeredDate);

    void updateUser(User user);

    void deleteUserById(int id);

    Optional<User> getUserAuth(String userName, String password);

}
