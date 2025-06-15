package com.rest.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rest.entities.Wallpaper;
import com.rest.persistence.IWallpaperDAO;
import com.rest.service.IWallpaperService;
@Service
public class WallpaperServiceImpl implements IWallpaperService{
    
    @Autowired
    private IWallpaperDAO wallpaperDAO;
    
    @Override
    public List<Wallpaper> getAllWallpapers() {
        return wallpaperDAO.getAllWallpapers();
    }
    
    @Override
    public Optional<Wallpaper> getWallpaperById(int id) {
        return wallpaperDAO.getWallpaperById(id);
    }
    
    @Override
    public Optional<Wallpaper> getWallpaperByTitle(String title) {
        return wallpaperDAO.getWallpaperByTitle(title);
    }
    
    @Transactional
    @Override
    public void addWallpaper(String title, String description, String filePath, String size, int state, int downloads, String registeredDate, int userId) {
        wallpaperDAO.addWallpaper(title, description, filePath, size, state, downloads, registeredDate, userId);
    }

    @Transactional
    @Override
    public void updateWallpaper(Wallpaper wallpaper) {
        wallpaperDAO.updateWallpaper(wallpaper);
    }

    @Transactional
    @Override
    public void deleteWallpaperById(int id) {
        wallpaperDAO.deleteWallpaperById(id);
    }

    @Override
    public List<Wallpaper> getWallpapersFromUser(int userId) {
        return wallpaperDAO.getWallpapersFromUser(userId);
    }
}
