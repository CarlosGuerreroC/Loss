package com.example.Backend.Controller;


import com.example.Backend.Model.EstadoReclamo;
import com.example.Backend.Model.Reclamo;
import com.example.Backend.Service.ReclamoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reclamos")
@RequiredArgsConstructor
public class ReclamoController {

    private final ReclamoService reclamoService;

    //Solicitar reclamo de objeto
    @PostMapping
    public ResponseEntity<?> crearReclamo(
            @RequestParam("estudianteId") Long estudianteId,
            @RequestParam("objetoId") Long objetoId){
        try {
            Reclamo nuevoReclamo = reclamoService.crearReclamo(estudianteId, objetoId);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoReclamo);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al procesar el reclamo "+ e.getMessage());
        }
    }

    //Cada estudiante puede ver su reclamo
    @GetMapping("/estudiante/{estudianteId}")
    public ResponseEntity<List<Reclamo>> obtenerPorEstudiante(@PathVariable Long estudianteId){
        List<Reclamo> misReclamos = reclamoService.obtenerReclamoPorEstudiante(estudianteId);
        return ResponseEntity.ok(misReclamos);
    }

    //El admin puede ver todos los reclamos
    @GetMapping("/admin/historial")
    public ResponseEntity<List<Reclamo>> obtenerTodo(){
        List<Reclamo> historial = reclamoService.obtenerTodosLosReclamos();
        return ResponseEntity.ok(historial);
    }

    //cambia el estado los reclamos (aprobado / rechazado )
    @PatchMapping("/{reclamoId}/estado")
    public ResponseEntity<?> cambiarEstadoReclamo(
            @PathVariable Long reclamoId,
            @RequestParam EstadoReclamo nuevoEstado){
        try {
            Reclamo actualizado = reclamoService.cambiarEstadoReclamo(reclamoId,nuevoEstado);
            return ResponseEntity.ok(actualizado);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error al actualizar el estado del reclamo "+ e.getMessage());
        }
    }

}
