package com.rest.controllers.dto;

public class UploadRequest {
    private String wallpaperName;
    private String wallpaperSize;

    public String getWallpaperName() {
        return wallpaperName;
    }

    public void setWallpaperName(String wallpaperName) {
        this.wallpaperName = wallpaperName;
    }

    public String getWallpaperSize() {
        return wallpaperSize;
    }

    public void setWallpaperSize(String wallpaperSize) {
        this.wallpaperSize = wallpaperSize;
    }
}
