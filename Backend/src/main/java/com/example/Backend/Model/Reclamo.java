package com.example.Backend.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "reclamos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reclamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario estudiante;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "objeto_id", nullable = false)
    private ObjetoPerdido objeto;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoReclamo estado;

    @Column(name = "fecha_reclamo", updatable = false)
    private LocalDateTime fechaReclamo;

    @PrePersist
    protected void onCreate(){
        this.fechaReclamo = LocalDateTime.now();
        if (this.estado == null){
            this.estado = EstadoReclamo.PENDIENTE;
        }
    }
}
