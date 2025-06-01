package com.rest.controllers.dto;

import com.rest.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WallpaperDTO {
    // This class is used to transfer data between the controller and the service layer
    // It is used to avoid exposing the entity directly to the client but it have the same data as the entity
    private int id;

    private String title;

    private String description;

    private String filePath;

    private String size;

    private int state;
    
    private int downloads;    

    private String registeredDate;

    private User user;
}
