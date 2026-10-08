package com.example.Backend.Service;

import com.example.Backend.Model.EstadoReclamo;
import com.example.Backend.Model.Reclamo;

import java.util.List;

public interface ReclamoService {
    Reclamo crearReclamo(Long estudianteId, Long objetoId);
    List<Reclamo> obtenerReclamoPorEstudiante(Long estudianteId);
    List<Reclamo> obtenerTodosLosReclamos();
    Reclamo cambiarEstadoReclamo(Long reclamoId, EstadoReclamo nuevoEstado);

}
