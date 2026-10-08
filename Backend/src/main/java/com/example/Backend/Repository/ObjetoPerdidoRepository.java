package com.example.Backend.Repository;

import com.example.Backend.Model.EstadoObjeto;
import com.example.Backend.Model.ObjetoPerdido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObjetoPerdidoRepository extends JpaRepository<ObjetoPerdido, Long> {

    List<ObjetoPerdido> findByEstado(EstadoObjeto estado);

    List<ObjetoPerdido> findByTituloContainigIgnoreCaseAndEstado(String titulo, EstadoObjeto estado);
}
