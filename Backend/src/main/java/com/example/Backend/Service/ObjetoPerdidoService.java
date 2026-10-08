package com.example.Backend.Service;

import com.example.Backend.Model.EstadoObjeto;
import com.example.Backend.Model.ObjetoPerdido;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ObjetoPerdidoService {
    ObjetoPerdido crearObjeto(String titulo, String descripcion, String ubicacion, MultipartFile imagen);
    List<ObjetoPerdido> obtenerPorEstado(EstadoObjeto estado);
    ObjetoPerdido cambiarEstado(Long id, EstadoObjeto nuevoEstado);
    void eliminarObjeto(Long id);
    ObjetoPerdido obtenerPorId(Long id);
}
