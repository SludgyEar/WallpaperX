package com.rest.persistence.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rest.entities.Wallpaper;
import com.rest.persistence.IWallpaperDAO;
import com.rest.repository.WallpaperRepository;
@Component
public class WallpaperDAOImpl implements IWallpaperDAO {

    @Autowired
    private WallpaperRepository wallpaperRepository;

    @Override
    public List<Wallpaper> getAllWallpapers() {
        return wallpaperRepository.getAllWallpapers();
    }

    @Override
    public Optional<Wallpaper> getWallpaperById(int id) {
        return wallpaperRepository.getWallpaperById(id);
    }

    @Override
    public Optional<Wallpaper> getWallpaperByTitle(String title) {
        return wallpaperRepository.getWallpaperByTitle(title);
    }

    @Override
    public void addWallpaper(String title, String description, String filePath, String size, int state, int downloads, String registeredDate, int userId) {
        wallpaperRepository.addWallpaper(title, description, filePath, size, state, downloads, registeredDate, userId);
    }

    @Override
    public void updateWallpaper(Wallpaper wallpaper) {
        wallpaperRepository.save(wallpaper);
    }

    @Override
    public void deleteWallpaperById(int id) {
        wallpaperRepository.deleteWallpaperById(id);
    }

    @Override
    public List<Wallpaper> getWallpapersFromUser(int userId) {
        return wallpaperRepository.getWallpapersFromUser(userId);
    }
}
