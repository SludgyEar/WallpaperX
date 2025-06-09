package com.rest.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rest.entities.User;
import com.rest.persistence.IUserDAO;
import com.rest.service.IUserService;
@Service
public class UserServiceImpl implements IUserService{

    @Autowired
    private IUserDAO userDAO;

    @Override
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    @Override
    public Optional<User> getUserById(int id) {
        return userDAO.getUserById(id);
    }

    @Override
    public Optional<User> getUserByUsername(String userName) {
        return userDAO.getUserByUsername(userName);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userDAO.getUserByEmail(email);
    }
    
    @Transactional
    @Override
    public void addUser(String userName, String email, String password, String rol, int state, String registeredDate) {
        userDAO.addUser(userName, email, password, rol, state, registeredDate);
    }
    
    @Transactional
    @Override
    public void updateUser(User user) {
        userDAO.updateUser(user);
    }

    @Transactional
    @Override
    public void deleteUserById(int id) {
        userDAO.deleteUserById(id);
    }

    @Override
    public Optional<User> getUserAuth(String userName, String password){
        return userDAO.getUserAuth(userName, password);
    }

    @Override
    public List<User> getUserByRol(String rol){
        return userDAO.getUserByRol(rol);
    }

    @Override
    public List<User> getUserByState(int state) {
        return userDAO.getUserByState(state);
    }
}
