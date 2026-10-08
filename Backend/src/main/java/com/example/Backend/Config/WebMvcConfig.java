package com.example.Backend.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResource(ResourceHandlerRegistry registro){
        //expone la carpeta local /uploads en la ruta http /uploads
        String uploadDir = Paths.get("uploads").toAbsolutePath().toUri().toString();
        registro.addResourceHandler("/uploads/**")
                .addResourceLocations(uploadDir);
    }
}
