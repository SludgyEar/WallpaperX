package com.rest.controllers.dto;
/*
 * Clase DTO para el manejo del esquema Upload 
*/
public class Upload {
    private String idUpload;
    private String wallpaperName;
    private String wallpaperSize;

    public String getIdUpload(){
        return this.idUpload;
    }
    public void setIdUpload(String idUpload){
        this.idUpload = idUpload;
    }

    public String getWallpaperName(){
        return this.wallpaperName;
    }
    public void setWallpaperName(String wallpaperName){
        this.wallpaperName = wallpaperName;
    }

    public String getWallpaperSize(){
        return this.wallpaperSize;
    }
    public void setWallpaperSize(String wallpaperSize){
        this.wallpaperSize = wallpaperSize;
    }
}
