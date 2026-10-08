package com.example.Backend.Dto;

import lombok.Data;

@Data
public class RegistroRequest {
    private String nombre;
    private String correo;
    private String contrasena;
}
