package com.rest.persistence.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rest.entities.User;
import com.rest.persistence.IUserDAO;
import com.rest.repository.UserRepository;
@Component
public class UserDAOImpl implements IUserDAO{
    
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    @Override
    public Optional<User> getUserById(int id) {
        return userRepository.getUserById(id);
    }

    @Override
    public Optional<User> getUserByUsername(String userName) {
        return userRepository.getUserByUsername(userName);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    @Override
    public void addUser(String userName, String email, String password, String rol, int state, String registeredDate) {
        userRepository.addUser(userName, email, password, rol, state, registeredDate);
    }

    @Override
    public void updateUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteUserById(int id) {
        userRepository.deleteUserById(id);
    }

    @Override
    public Optional<User> getUserAuth(String username, String password){
        return userRepository.getUserAuth(username, password);
    }

}
