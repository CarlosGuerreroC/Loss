package com.example.Backend.Service;

import com.example.Backend.Model.EstadoObjeto;
import com.example.Backend.Model.ObjetoPerdido;
import com.example.Backend.Repository.ObjetoPerdidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ObjetoPerdidoServiceImpl implements ObjetoPerdidoService{

    private final ObjetoPerdidoRepository objetoRepository;
    private final FileStorageService fileStorageService;

    @Override
    public ObjetoPerdido crearObjeto(String titulo, String descripcion, String ubicacion, MultipartFile imagen){
        String urlImagen = fileStorageService.guardarArchivo(imagen);

        ObjetoPerdido objeto = new ObjetoPerdido();
        objeto.setTitulo(titulo);
        objeto.setDescripcion(descripcion);
        objeto.setUbicacionHallazgo(ubicacion);
        objeto.setUrlImagen(urlImagen);
        objeto.setEstado(EstadoObjeto.DISPONIBLE);

        return objetoRepository.save(objeto);
    }

    @Override
    public List<ObjetoPerdido> obtenerPorEstado(EstadoObjeto estado){
        return objetoRepository.findByEstado(estado);
    }

    @Override
    public ObjetoPerdido cambiarEstado(Long id,EstadoObjeto nuevoestado){
        ObjetoPerdido objeto = obtenerPorId(id);
        objeto.setEstado(nuevoestado);
        return objetoRepository.save(objeto);
    }

    @Override
    public void eliminarObjeto(Long id){
        if (!objetoRepository.existsById(id)){
            throw new RuntimeException("El objeto no existe");
        }
        objetoRepository.deleteById(id);
    }

    @Override
    public ObjetoPerdido obtenerPorId(Long id){
        return objetoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Objeto con ID "+ id + "no encontrado"));
    }
}
