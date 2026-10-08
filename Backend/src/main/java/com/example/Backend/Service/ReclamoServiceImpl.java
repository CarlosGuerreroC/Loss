package com.example.Backend.Service;

import com.example.Backend.Model.*;
import com.example.Backend.Repository.ObjetoPerdidoRepository;
import com.example.Backend.Repository.ReclamoRepository;
import com.example.Backend.Repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReclamoServiceImpl implements ReclamoService{

    private final ReclamoRepository reclamoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ObjetoPerdidoRepository objetoRepository;

    @Override
    public Reclamo crearReclamo(Long estudianteId, Long objetoId){
        Usuario estudiante = usuarioRepository.findById(estudianteId)
                .orElseThrow(() -> new RuntimeException("El usuario no existe"));

        ObjetoPerdido objeto = objetoRepository.findById(objetoId)
                .orElseThrow(() -> new RuntimeException("El objeto no existe"));

        if (objeto.getEstado() == EstadoObjeto.ENTREGADO){
            throw new RuntimeException("El objeto ya ha sido entregado");
        }

        if (reclamoRepository.existsByEstudianteIdAndObjetoId(estudianteId, objetoId)){
            throw new RuntimeException("Ya has enviado una solicitud para reclamar este objeto");
        }

        Reclamo reclamo = new Reclamo();
        reclamo.setEstudiante(estudiante);
        reclamo.setObjeto(objeto);
        reclamo.setEstado(EstadoReclamo.PENDIENTE);

        return reclamoRepository.save(reclamo);
    }

    @Override
    public List<Reclamo> obtenerReclamoPorEstudiante(Long estudianteId){
        return reclamoRepository.findByEstudianteId(estudianteId);
    }

    @Override
    public List<Reclamo> obtenerTodosLosReclamos(){
        return reclamoRepository.findAll();
    }

    @Override
    public Reclamo cambiarEstadoReclamo(Long reclamoId, EstadoReclamo nuevoEstado){
        Reclamo reclamo = reclamoRepository.findById(reclamoId)
                .orElseThrow(() -> new RuntimeException("El reclamo no existe"));

        reclamo.setEstado(nuevoEstado);

        //Si el reclamo es aprobado, cambia el objeto a Entregado
        if (nuevoEstado == EstadoReclamo.APROBADO){
            ObjetoPerdido objeto = reclamo.getObjeto();
            objeto.setEstado(EstadoObjeto.ENTREGADO);
            objetoRepository.save(objeto);
        }
        return  reclamoRepository.save(reclamo);
    }
}
