package com.rest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.rest.entities.Wallpaper;
// This class will handle custom querys    

@Repository
public interface WallpaperRepository extends CrudRepository<Wallpaper, Integer>{
    @Query("SELECT w FROM Wallpaper w")
    List<Wallpaper> getAllWallpapers();

    @Query("SELECT w FROM Wallpaper w WHERE w.id = ?1")
    Optional<Wallpaper> getWallpaperById(int id);

    @Query("SELECT w FROM Wallpaper w WHERE w.title = ?1")
    Optional<Wallpaper> getWallpaperByTitle(String title);

    @Modifying
    @Transactional
    @Query(
        value = "INSERT INTO Wallpaper (title, description, file_path, size, state, downloads, registered_date, user_id) VALUES (?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8)",
        nativeQuery = true)
    void addWallpaper(String title, String description, String filePath, String size, int state, int downloads, String registeredDate, int userId);

    // The update method is not needed in this case, as the User entity is managed by Spring Data JPA
    // Altough, you can define a custom update method if needed

    @Modifying
    @Transactional
    @Query("DELETE FROM Wallpaper w WHERE w.id = ?1")
    void deleteWallpaperById(int id);

    @Query("SELECT w FROM Wallpaper w WHERE w.user.id = ?1")
    List<Wallpaper> getWallpapersFromUser(int userId);
}
