package com.rest.controllers;

import java.util.Optional;
import java.util.List;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rest.controllers.dto.WallpaperDTO;
import com.rest.entities.Wallpaper;
import com.rest.service.IWallpaperService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@RequestMapping("/api/wallpaper")
public class WallpaperController {
    /*
     * This class is used to handle the requests from the client
     * It is used to transfer data between the controller and the service layer
     * It is used to avoid exposing the entity directly to the client but it have the same data as the entity
     */
    
    @Autowired
    private IWallpaperService wallpaperService;

    @GetMapping("/wallpaperById/{id}")
    public ResponseEntity<?> getWallpaperById(@PathVariable int id) {
        // Start search for wallpaper by id
        Optional<Wallpaper> wallpaperOptional = wallpaperService.getWallpaperById(id);
        
        // If exists
        if(wallpaperOptional.isPresent()){
            // Instead of returning the wallpaper entity, we return a DTO object
            // So we need to catch the wallpaper entity with the get() method
            // and then we make a new DTO object with the wallpaper entity data
            Wallpaper wallpaper = wallpaperOptional.get();
            WallpaperDTO wallpaperDTO = WallpaperDTO.builder()
            .id(wallpaper.getId())
            .title(wallpaper.getTitle())
            .description(wallpaper.getDescription())
            .filePath(wallpaper.getFilePath())
            .size(wallpaper.getSize())
            .state(wallpaper.getState())
            .downloads(wallpaper.getDownloads())
            .registeredDate(wallpaper.getRegisteredDate())
            .user(wallpaper.getUser())
            .build();
            return ResponseEntity.ok(wallpaperDTO);
        }

        return ResponseEntity.notFound().build();
    }
        
    @GetMapping("/allWallpapers")
    public ResponseEntity<?> getAllWallpapers() {
        
        List<WallpaperDTO> wallpaperList = wallpaperService.getAllWallpapers()
        .stream()
        .map(wallpaper -> WallpaperDTO.builder()
            .id(wallpaper.getId())
            .title(wallpaper.getTitle())
            .description(wallpaper.getDescription())
            .filePath(wallpaper.getFilePath())
            .size(wallpaper.getSize())
            .state(wallpaper.getState())
            .downloads(wallpaper.getDownloads())
            .registeredDate(wallpaper.getRegisteredDate())
            .user(wallpaper.getUser())
            .build())
        .toList();

        return ResponseEntity.ok(wallpaperList);
    }
    
    @GetMapping("/wallpaparByTtile/{title}")
    public ResponseEntity<?> getWallpaperByTitle(@PathVariable String title){

        Optional<Wallpaper> wallpaperOptional = wallpaperService.getWallpaperByTitle(title);
        if(wallpaperOptional.isPresent()){
            Wallpaper wallpaper = wallpaperOptional.get();
            WallpaperDTO wallpaperDTO = WallpaperDTO.builder()
            .id(wallpaper.getId())
            .title(wallpaper.getTitle())
            .description(wallpaper.getDescription())
            .filePath(wallpaper.getFilePath())
            .size(wallpaper.getSize())
            .state(wallpaper.getState())
            .downloads(wallpaper.getDownloads())
            .registeredDate(wallpaper.getRegisteredDate())
            .user(wallpaper.getUser())
            .build();
            return ResponseEntity.ok(wallpaperDTO);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/upload")
    public ResponseEntity<?> addWallpaper(@RequestBody WallpaperDTO wallpaperDTO) throws URISyntaxException {
        //First we recieve the wallpaperDTO object from the request body
        //Then we validate if the wallpaperDTO dont contains null values
        //If it contains null values we return a bad request response
        if(wallpaperDTO.getTitle().isBlank()){
            return ResponseEntity.badRequest().build();
        }
        // If it's valid we use WallpaperDTO to create a new wallpaper
        // This is this way cause we are using a Native Query to insert the wallpaper
        // and we need to pass the parameters to the query
        wallpaperService.addWallpaper(
            wallpaperDTO.getTitle(),
            wallpaperDTO.getDescription(),
            wallpaperDTO.getFilePath(),
            wallpaperDTO.getSize(),
            wallpaperDTO.getState(),
            wallpaperDTO.getDownloads(),
            LocalDateTime.now().toString(), // We use LocalDateTime.now() to get the current date and time
            wallpaperDTO.getUser().getId()
        );
        return ResponseEntity.created(new URI("api/wallpaper/add")).build();
    }

    @PutMapping("/modify/{id}")
    public ResponseEntity<?> updateWallpaper(@PathVariable int id, @RequestBody WallpaperDTO wallpaperDTO) {
        
        Optional<Wallpaper> wallpaperOptional = wallpaperService.getWallpaperById(id);
        if(wallpaperOptional.isPresent()){
            Wallpaper wallpaper = wallpaperOptional.get();
            wallpaper.setTitle(wallpaperDTO.getTitle());

            wallpaperService.updateWallpaper(wallpaper);
            return ResponseEntity.ok("Wallpaper updated successfully");
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteWallpaperById(@PathVariable int id){
        if(id > 0){
            wallpaperService.deleteWallpaperById(id);
            return ResponseEntity.ok("Wallpaper deleted successfully");
        }
        return ResponseEntity.badRequest().build();
    }
    
}
