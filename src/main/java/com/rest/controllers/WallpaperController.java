package com.rest.controllers;

import java.util.Optional;
import java.util.List;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.controllers.dto.WallpaperDTO;
import com.rest.entities.User;
import com.rest.entities.Wallpaper;
import com.rest.service.IUserService;
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
     * It is used to avoid exposing the entity directly to the client but it have
     * the same data as the entity
     */

    @Autowired
    private IWallpaperService wallpaperService;
    @Autowired
    private IUserService userService;

    @GetMapping("/wallpaperById/{id}")
    public ResponseEntity<?> getWallpaperById(@PathVariable int id) {
        // Start search for wallpaper by id
        Optional<Wallpaper> wallpaperOptional = wallpaperService.getWallpaperById(id);

        // If exists
        if (wallpaperOptional.isPresent()) {
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
    public ResponseEntity<?> getWallpaperByTitle(@PathVariable String title) {

        Optional<Wallpaper> wallpaperOptional = wallpaperService.getWallpaperByTitle(title);
        if (wallpaperOptional.isPresent()) {
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

    @GetMapping("/wallapersFromUser/{userId}")
    public ResponseEntity<?> getWallpapersFromUser(@PathVariable int userId) {
        Optional<User> userOptional = userService.getUserById(userId);
        if (userOptional.isPresent()) {
            List<WallpaperDTO> wallpaperList = wallpaperService.getWallpapersFromUser(userId)
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
        return ResponseEntity.notFound().build();
    }

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<?> addWallpaper(@RequestParam MultipartFile file,
            @RequestParam("wallpaper") String wallpaperJson) throws URISyntaxException {
        try {
            // We use ObjectMapper to convert the JSON string to a WallpaperDTO object
            ObjectMapper objectMapper = new ObjectMapper();
            WallpaperDTO wallpaperDTO = objectMapper.readValue(wallpaperJson, WallpaperDTO.class);

            // First we recieve the wallpaperDTO object from the client
            // Then we validate if the wallpaperDTO dont contains null values
            // If it contains null values we return a bad request response
            if (wallpaperDTO.getTitle().isBlank()) {
                return ResponseEntity.badRequest().body("El título no puede estar vacío.");
            }
            if (wallpaperDTO.getUser().getId() <= 0) {
                return ResponseEntity.badRequest().body("ID de usuario inválido.");
            }
            

            // Validate if the dir exists, if not we create it
            String projectPath = System.getProperty("user.dir");
            String uploadDir = projectPath + "/images/";

            File uploadFolder = new File(uploadDir);
            if (!uploadFolder.exists()) {
                uploadFolder.mkdirs();
            }
            // Uploading the file to the server
            String absolutePath = uploadDir + file.getOriginalFilename();
            file.transferTo(new File(absolutePath));
            String filePath = absolutePath.split("wallpaperx")[1];

            // Gettin real size of the file in bytes
            long sizeInBytes = file.getSize();
            String sizeFormatted = String.format("%.2f MB", sizeInBytes / (1024.0 * 1024.0));

            // If it's valid we use WallpaperDTO to create a new wallpaper
            // This is this way cause we are using a Native Query to insert the wallpaper
            // and we need to pass the parameters to the query
            wallpaperService.addWallpaper(
                    wallpaperDTO.getTitle(),
                    wallpaperDTO.getDescription(),
                    filePath, // FilePath guardado
                    sizeFormatted, // Tamaño calculado
                    wallpaperDTO.getState(),
                    0, // Zero downloads by default
                    LocalDateTime.now().toString().split("T")[0],
                    wallpaperDTO.getUser().getId());

            return ResponseEntity.created(new URI("api/wallpaper/upload")).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error al subir wallpaper.");
        }

    }

    @PutMapping("/modify/{id}")
    public ResponseEntity<?> updateWallpaper(@PathVariable int id, @RequestBody WallpaperDTO wallpaperDTO) {

        Optional<Wallpaper> wallpaperOptional = wallpaperService.getWallpaperById(id);
        if (wallpaperOptional.isPresent()) {
            Wallpaper wallpaper = wallpaperOptional.get();
            wallpaper.setTitle(wallpaperDTO.getTitle());

            wallpaperService.updateWallpaper(wallpaper);
            return ResponseEntity.ok("Wallpaper updated successfully");
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteWallpaperById(@PathVariable int id) {
        if (id > 0) {
            wallpaperService.deleteWallpaperById(id);
            return ResponseEntity.ok("Wallpaper deleted successfully");
        }
        return ResponseEntity.badRequest().build();
    }

}
