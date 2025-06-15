package com.rest.controllers;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rest.controllers.dto.UserDTO;
import com.rest.entities.User;
import com.rest.service.IUserService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@RequestMapping("/api/user")
public class UserController {
    /*
     * This class is used to handle the requests from the client
     * It is used to transfer data between the controller and the service layer
     * It is used to avoid exposing the entity directly to the client but it have
     * the same data as the entity
     */
    
     @Autowired
     private IUserService userService;

     @GetMapping("/userById/{id}")
     public ResponseEntity<?> getUserById(@PathVariable int id){
        // Search a single user by id
        Optional<User> userOptional = userService.getUserById(id);
        // If exists
        if(userOptional.isPresent()){
            // Instead of returning the user entity, we return a DTO object
            // So we need to catch the user entity with the get() method
            // and then we make a new DTO object with the user entity data
            User user = userOptional.get();
            UserDTO userDTO = UserDTO.builder()
            .id(user.getId())
            .userName(user.getUserName())
            .email(user.getEmail())
            .password(user.getPassword())
            .rol(user.getRol())
            .state(user.getState())
            .registeredDate(user.getRegisteredDate())
            .build();
            return ResponseEntity.ok(userDTO);
        }
        return ResponseEntity.notFound().build();
     }

     @GetMapping("/allUsers")
     public ResponseEntity<?> getAllUsers(){

        List<UserDTO> userList = userService.getAllUsers()
        .stream()
        .map(user -> UserDTO.builder()
            .id(user.getId())
            .userName(user.getUserName())
            .email(user.getEmail())
            .password(user.getPassword())
            .rol(user.getRol())
            .state(user.getState())
            .registeredDate(user.getRegisteredDate())
            .build())
        .toList();

        return ResponseEntity.ok(userList);
     }
     
     @GetMapping("/userByName/{username}")
     public ResponseEntity<?> getUserByUsername(@PathVariable String username){
        // Search a single user by username
        Optional<User> userOptional = userService.getUserByUsername(username);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            UserDTO userDTO = UserDTO.builder()
            .id(user.getId())
            .userName(user.getUserName())
            .email(user.getEmail())
            .password(user.getPassword())
            .rol(user.getRol())
            .state(user.getState())
            .registeredDate(user.getRegisteredDate())
            .build();
            return ResponseEntity.ok(userDTO);
        }

        return ResponseEntity.notFound().build();
     }

     @GetMapping("/userByEmail/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email){
        // Search a single user by email
        Optional<User> userOptional = userService.getUserByEmail(email);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            UserDTO userDTO = UserDTO.builder()
            .id(user.getId())
            .userName(user.getUserName())
            .email(user.getEmail())
            .password(user.getPassword())
            .rol(user.getRol())
            .state(user.getState())
            .registeredDate(user.getRegisteredDate())
            .build();
            return ResponseEntity.ok(userDTO);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/userByRol/{rol}")
    public ResponseEntity<?> getUserByRol(@PathVariable String rol){
        // Search a single user by rol
        rol = rol.toUpperCase();
        List<UserDTO> userList = userService.getUserByRol(rol)
        .stream()
        .map(user -> UserDTO.builder()
            .id(user.getId())
            .userName(user.getUserName())
            .email(user.getEmail())
            .password(user.getPassword())
            .rol(user.getRol())
            .state(user.getState())
            .registeredDate(user.getRegisteredDate())
            .build())
        .toList();
        return ResponseEntity.ok(userList);
    }

    @GetMapping("/userByState/{state}")
    public ResponseEntity<?> getUserByState(@PathVariable String state){
        int stateNum = 0;
        if(state.equals("ACTIVO") || state.equals("activo")){
            stateNum = 1;
        }else if(state.equals("BANEADO") || state.equals("baneado")){
            stateNum = 2;
        }else{
            stateNum = 0; // INACTIVO
        }
        List<UserDTO> userList = userService.getUserByState(stateNum)
        .stream()
        .map(user -> UserDTO.builder()
            .id(user.getId())
            .userName(user.getUserName())
            .email(user.getEmail())
            .password(user.getPassword())
            .rol(user.getRol())
            .state(user.getState())
            .registeredDate(user.getRegisteredDate())
            .build())
        .toList();
        return ResponseEntity.ok(userList);
    }
    

    @PostMapping("/register")
    public ResponseEntity<?> addUser(@RequestBody UserDTO userDTO) throws URISyntaxException{
        // First we recieve the userDTO object from the request body
        // Then we validate if the userDTO dont contains null values
        // If it contains null values we return a bad request response
        if(userDTO.getUserName().isBlank() || userDTO.getEmail().isBlank()){
            return ResponseEntity.badRequest().build();
        }
        // Then we validate if the userDTO already exists in the database
        Optional<User> userOptional = userService.getUserByEmail(userDTO.getEmail());
        if(userOptional.isPresent()){
            // If he does, we return a bad request response
            return ResponseEntity.badRequest().body("User already exists");
        }
        // If it's valid we use userDTO to create a new user
        // This is this way cause we are using a Native Query to insert the user
        // and we need to pass the parameters to the query
        userService.addUser(
            userDTO.getUserName(),
            userDTO.getEmail(),
            userDTO.getPassword(),
            userDTO.getRol(),
            userDTO.getState(),
            LocalDateTime.now().toString().split("T")[0] // Get the current date in yyyy-MM-dd format
        );
        return ResponseEntity.created(new URI("api/user/register")).build();
    }

    @PutMapping("/modify/{id}")
    public ResponseEntity<?> updateUser(@PathVariable int id, @RequestBody UserDTO userDTO){
        Optional<User> userOptional = userService.getUserById(id);
        if(userOptional.isPresent()){
            User user = userOptional.get();

            if(userDTO.getUserName() != null){
                user.setUserName(userDTO.getUserName());
            }
            if(userDTO.getEmail() != null){
                user.setEmail(userDTO.getEmail());
            }
            if(userDTO.getPassword() != null){
                user.setPassword(userDTO.getPassword());
            }
            if(userDTO.getRol() != null){
                user.setRol(userDTO.getRol().toUpperCase());
            }
            if((userDTO.getState() >= 0 && (userDTO.getState() < 3))){
                user.setState(userDTO.getState());
            }
            userService.updateUser(user);
            return ResponseEntity.ok("Updated successfully");
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id){
        // First we check if the id is validate
        // If the id is valid we use the userService to delete the user by id
        if(id > 0){
            userService.deleteUserById(id);
            return ResponseEntity.ok("User deleted successfully");
        }
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping("/auth")
    public ResponseEntity<?> userAuth(@RequestBody UserDTO userDTO) throws URISyntaxException{
        // First we recive a raw Json or an object from the request body
        // Then we validate if the username and password contains null values
        if(userDTO.getUserName().isBlank() || userDTO.getPassword().isBlank()){
            return ResponseEntity.badRequest().body("Authentication failed: username or password cannot be empty");
        }
        // If it's valid we use the userService to authenticate the user and if the user exists and the password is correct
        // we return a 201 Created response with a message
        Optional<User> userOptional = userService.getUserAuth(userDTO.getUserName(), userDTO.getPassword());
        if(userOptional.isPresent()){
            return ResponseEntity.created(new URI("api/user/auth")).body(userOptional.get());
        }
        return ResponseEntity.status(401).body("Authentication failed: Invalid username or password");
    }
    
}
