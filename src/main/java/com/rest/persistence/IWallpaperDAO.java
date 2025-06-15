package com.rest.persistence;

/*
* In this interface, you can define methods for wallpaper-related database operations
*/

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import com.rest.entities.Wallpaper;

@Repository
public interface IWallpaperDAO {
    List<Wallpaper> getAllWallpapers();

    Optional<Wallpaper> getWallpaperById(int id);

    Optional<Wallpaper> getWallpaperByTitle(String title);

    void addWallpaper(String title, String description, String filePath, String size, int state, int downloads, String registeredDate, int userId);

    void updateWallpaper(Wallpaper wallpaper);

    void deleteWallpaperById(int id);

    List<Wallpaper> getWallpapersFromUser(int userId);
}
