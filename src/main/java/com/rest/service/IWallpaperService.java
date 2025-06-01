package com.rest.service;

import java.util.List;
import java.util.Optional;

import com.rest.entities.Wallpaper;

public interface IWallpaperService {
    List<Wallpaper> getAllWallpapers();

    Optional<Wallpaper> getWallpaperById(int id);

    Optional<Wallpaper> getWallpaperByTitle(String title);

    void addWallpaper(String title, String description, String filePath, String size, int state, int downloads, String registeredDate, int userId);

    void updateWallpaper(Wallpaper wallpaper);

    void deleteWallpaperById(int id);
}
