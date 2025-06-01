// package com.wallpaper.rest.controllers;

// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;

// import com.wallpaper.rest.controllers.dto.GenericResponse;
// import com.wallpaper.rest.controllers.dto.Upload;

// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestBody;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;


// @RestController
// public class UploadController {

// /**
//  * @param idUpload
//  * @param wallpaperName
//  * @param wallpaperSize
//  * @return retorna el objeto correspondiente a la carga de wallpaper
//  */

//     private static final Logger LOGGER = LoggerFactory.getLogger( UploadController.class);
    

//     @GetMapping("/upload")
//     public Upload getUploadById(@RequestParam(value = "id") String idUpload, @RequestParam(value = "name") String wallpaperName, @RequestParam(value = "size") String wallpaperSize) {
//         Upload upload = new Upload();
//         upload.setIdUpload(idUpload);
//         upload.setWallpaperName(wallpaperName);
//         upload.setWallpaperSize(wallpaperSize);

//         LOGGER.info("Hola este es un Mensaje informativo");
//         LOGGER.error("Hola este es un Mensaje de error");
//         LOGGER.debug("Hola este es un Mensaje de debug");

//         return upload;
//     }

//     @PostMapping(value = "/upload", consumes = "application/json", produces = "application/json")
//     public Upload createUpload(@RequestBody Upload upload){
//         Upload newUpload = new Upload();
//         newUpload.setIdUpload(upload.getIdUpload());
//         newUpload.setWallpaperName(upload.getWallpaperName());
//         newUpload.setWallpaperSize(upload.getWallpaperSize());
//         return newUpload;
//     }
//     // Podríamos tener más de un pathParameter
//     @PutMapping("/upload/{id}")
//     public Upload updateUpload(@PathVariable (value = "id") String idUpload, @RequestBody Upload upload){
//         Upload updatedUpload = new Upload();
//         updatedUpload.setIdUpload(idUpload);
//         updatedUpload.setWallpaperName(upload.getWallpaperName());
//         updatedUpload.setWallpaperSize(upload.getWallpaperSize());
//         return updatedUpload;
//     }
    
//     @DeleteMapping("/upload/{id}")
//     public GenericResponse deleteUpload(@PathVariable (value = "id") String idUpload){
//         GenericResponse response = new GenericResponse();
//         response.setCode(200);
//         response.setDescription("Upload deleted successfully");
//         return response;
//     }
// }
