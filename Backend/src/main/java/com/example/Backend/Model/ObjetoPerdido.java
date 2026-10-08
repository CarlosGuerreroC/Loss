package com.example.Backend.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "objeto_perdido")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ObjetoPerdido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 150)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "ubicacion_hallazgo", nullable = false)
    private String ubicacionHallazgo;

    @Column(name = "url_imagen")
    private String urlImagen;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoObjeto estado;

    @Column(name = "fecha_registro", updatable = false)
    private LocalDateTime fechaRegistro;

    @PrePersist
    protected void onCreate(){
        this.fechaRegistro = LocalDateTime.now();
        if (this.estado == null){
            this.estado = EstadoObjeto.DISPONIBLE;
        }
    }
}
