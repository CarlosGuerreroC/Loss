package com.example.Backend.Controller;

import com.example.Backend.Model.EstadoObjeto;
import com.example.Backend.Model.ObjetoPerdido;
import com.example.Backend.Service.ObjetoPerdidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/objetos")
@RequiredArgsConstructor
public class ObjetoPerdidoController {

    private final ObjetoPerdidoService objetoPerdidoService;

    // Publicar el objeto perdido con foto
    // se usa consume MediaType.MULTIPART_FORM_DATA_VALUE para aceptar form-data con archivos
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> crearObjeto(
            @RequestParam("titulo") String titulo,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("ubicacionHallazgo") String ubicacionHallazgo,
            @RequestParam(value = "imagen", required = false)MultipartFile imagen){

        try {
            ObjetoPerdido nuevoObjeto = objetoPerdidoService.crearObjeto(
                    titulo,
                    descripcion,
                    ubicacionHallazgo,
                    imagen
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoObjeto);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al registrar el objeto "+e.getMessage());
        }
    }

    //Listar objetos perdidos disponibles
    @GetMapping("/perdidos")
    public ResponseEntity<List<ObjetoPerdido>> obtenerDisponibles(){
        List<ObjetoPerdido> disponibles = objetoPerdidoService.obtenerPorEstado(EstadoObjeto.DISPONIBLE);
        return ResponseEntity.ok(disponibles);
    }

    //Listar objetos entregados
    @GetMapping("/entregados")
    public ResponseEntity<List<ObjetoPerdido>> obtenerEntregados(){
        List<ObjetoPerdido> entregados = objetoPerdidoService.obtenerPorEstado(EstadoObjeto.ENTREGADO);
        return ResponseEntity.ok(entregados);
    }

    //Cambiar estado del objeto
    @PatchMapping("/{id}/estado")
    public ResponseEntity<?> cambiarEstado(
            @PathVariable Long id,
            @RequestParam EstadoObjeto nuevoEstado
    ){
        try {
            ObjetoPerdido actualizado = objetoPerdidoService.cambiarEstado(id,nuevoEstado);
            return ResponseEntity.ok(actualizado);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error "+ e.getMessage());
        }
    }

    //Eliminar objeto perdido
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarObjeto(@PathVariable Long id){
        try {
            objetoPerdidoService.eliminarObjeto(id);
            return ResponseEntity.ok("Objeto eliminado correctamente");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error al eliminar "+ e.getMessage());
        }
    }
}
