package com.example.Backend.Controller;


import com.example.Backend.Dto.AuthResponse;
import com.example.Backend.Dto.LoginRequest;
import com.example.Backend.Dto.RegistroRequest;
import com.example.Backend.Model.Rol;
import com.example.Backend.Model.Usuario;
import com.example.Backend.Repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioRepository usuarioRespository;

    // Registro de estudiantes
    @PostMapping("/register/estudiante")
    public ResponseEntity<?> registrarEstudiante(@RequestBody RegistroRequest request ){
        if (usuarioRespository.existsByCorreo(request.getCorreo())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("El correo ya se encuentra registrado");
        }

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(request.getNombre());
        nuevoUsuario.setCorreo(request.getCorreo());
        nuevoUsuario.setContrasena(request.getContrasena());
        nuevoUsuario.setRol(Rol.ESTUDIANTE);

        Usuario guardado = usuarioRespository.save(nuevoUsuario);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new AuthResponse(
                        guardado.getId(),
                        guardado.getNombre(),
                        guardado.getCorreo(),
                        guardado.getRol(),
                        "Estudiante registrado exitosamente"
                )
        );
    }

    @PostMapping("/register/admin")
    public ResponseEntity<?> registrarAdmin(@RequestBody RegistroRequest request ){
        if (usuarioRespository.existsByCorreo(request.getCorreo())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("El correo ya se encuentra registrado");
        }

        Usuario nuevoAdmin = new Usuario();
        nuevoAdmin.setNombre(request.getNombre());
        nuevoAdmin.setCorreo(request.getCorreo());
        nuevoAdmin.setContrasena(request.getContrasena());
        nuevoAdmin.setRol(Rol.ADMIN);

        Usuario guardado = usuarioRespository.save(nuevoAdmin);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new AuthResponse(
                        guardado.getId(),
                        guardado.getNombre(),
                        guardado.getCorreo(),
                        guardado.getRol(),
                        "Administrador registrado exitosamente"
                )
        );
    }

    @PostMapping("/Login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request ){
        Optional<Usuario> usuarioOpt = usuarioRespository.findByCorreo(request.getCorreo());

        if (usuarioOpt.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("El usuario no existe");
        }

        Usuario usuario = usuarioOpt.get();
        //Validacion simple de contrasena
        if (!usuario.getContrasena().equals(request.getContrasena())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Contraseña incorrecta");
        }

        return ResponseEntity.ok(
                new AuthResponse(
                        usuario.getId(),
                        usuario.getNombre(),
                        usuario.getCorreo(),
                        usuario.getRol(),
                        "Inicio de sesion exitoso"
                )
        );
    }


}
