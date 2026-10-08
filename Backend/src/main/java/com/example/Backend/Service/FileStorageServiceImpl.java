package com.example.Backend.Service;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageServiceImpl implements FileStorageService {
    private final Path uploadLocation = Paths.get("uploads").toAbsolutePath().normalize();

    public FileStorageServiceImpl(){
        try{
            Files.createDirectories(this.uploadLocation);
        }catch (Exception ex){
            throw new RuntimeException("No se pudo crear la carpeta donde se almacenaran las imagenes", ex);
        }
    }

    @Override
    public  String guardarArchivo(MultipartFile file){
        if (file == null || file.isEmpty()){
            return null;
        }
        try {
            //Asigna un nombre unico para no sobrescribir
            String extension = obtenerExtension(file.getOriginalFilename());
            String nombreArchivo = UUID.randomUUID().toString() + extension;
            Path targetLocation = this.uploadLocation.resolve(nombreArchivo);

            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            //Devuelve la ruta relativa que se guardara en la BD
            return "/uploads/" + nombreArchivo;
        } catch (IOException ex) {
            throw new RuntimeException("Error al guardar la imagen. Intentalo de nuevo", ex);
        }
    }

    private String obtenerExtension(String nombreOriginal){
        if (nombreOriginal != null && nombreOriginal.contains(".")){
            return nombreOriginal.substring(nombreOriginal.lastIndexOf("."));
        }
        return ".jpg";
    }
}
