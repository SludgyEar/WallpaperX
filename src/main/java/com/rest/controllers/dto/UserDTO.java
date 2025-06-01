package com.rest.controllers.dto;

import java.util.ArrayList;
import java.util.List;

import com.rest.entities.Wallpaper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    // This class is used to transfer data between the controller and the service layer
    // It is used to avoid exposing the entity directly to the client but it have the same data as the entity
    private int id;

    private String userName;

    private String email;

    private String password;
    
    private String rol;
    
    private int state;

    private String registeredDate;

    @Builder.Default
    private List<Wallpaper> wallpaperList = new ArrayList<>();
}
