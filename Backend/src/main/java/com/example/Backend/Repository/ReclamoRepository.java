package com.example.Backend.Repository;

import com.example.Backend.Model.EstadoReclamo;
import com.example.Backend.Model.Reclamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReclamoRepository extends JpaRepository<Reclamo, Long> {

    List<Reclamo> findByEstudianteId(Long estudianteId);

    List<Reclamo> findByObjetoId(Long objetoId);

    List<Reclamo> findByEstado(EstadoReclamo estado);

    boolean existsByEstudianteIdAndObjetoId(Long estudianteId, Long objetoId);

    Optional<Reclamo> findByEstudianteIdAndObjetoId(Long estudianteId, Long objetoId);
}
