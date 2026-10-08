package com.example.Backend.Dto;

import com.example.Backend.Model.Rol;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private Long id;
    private String nombre;
    private String correo;
    private Rol rol;
    private String mensaje;
}
