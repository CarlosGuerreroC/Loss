package com.example.Backend.Repository;

import com.example.Backend.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    //Busca un usuario por el correo
    Optional<Usuario> findByCorreo(String correo);

    // Verificar si ya existe un usuario registrado con ese correo
    boolean existsByCorreo(String correo);
}
