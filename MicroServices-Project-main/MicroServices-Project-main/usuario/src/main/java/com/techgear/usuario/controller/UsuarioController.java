package com.techgear.usuario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techgear.usuario.model.Usuario;
import com.techgear.usuario.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.techgear.usuario.dto.LoginRequest;

@RestController
@RequestMapping("/usuario")
@Tag(name = "Usuarios",description = "Operaciones CRUD de usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping()
    @Operation(summary="Obtener todos los usuarios",description = "Obtiene una lista de todos los usuarios")
    @ApiResponse(responseCode = "200", description = "Listado de usuarios",
    content = @Content(mediaType = "application/json",
        array = @ArraySchema(schema = @Schema(implementation = Usuario.class)))
    )
    public ResponseEntity<List<Usuario>> obtenerUsuarios(){
        try {
            List<Usuario>usuarios = usuarioService.getAllUsuarios();
            if (usuarios.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    @Operation(summary="Obtener usuario",description = "Obtiene un usuario mediante ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario encontrado", 
            content = @Content(schema = @Schema(implementation = Usuario.class))),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<Usuario> obtenerUsuario(@PathVariable("id")Integer id){
        try {
            Usuario usuario = usuarioService.getUsuario(id);
            if (usuario==null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }


    @PostMapping()
    @Operation(summary="Ingresar usuario", description = "Ingresa un nuevo usuario a la BD utilizando un JSON")
        // --- ANOTACIONES DE DOCUMENTACIÓN ACTUALIZADAS ---
    @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Usuario creado exitosamente",
        content = @Content(schema = @Schema(implementation = Usuario.class))),
    @ApiResponse(responseCode = "409", description = "El RUT o el correo ya se encuentran registrados",
        content = @Content(schema = @Schema(implementation = String.class))), // El cuerpo ahora es un String
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<?> insertarUsuario(@RequestBody Usuario usuario) { // Cambiado a ResponseEntity<?> para permitir diferentes tipos de cuerpo
    try {
        Usuario newUsuario = usuarioService.saveUsuario(usuario);
        return ResponseEntity.ok(newUsuario);

    } catch (DataIntegrityViolationException e) {
        // Esta excepción se lanza cuando se viola una restricción UNIQUE (rut o correo duplicado)
        return ResponseEntity
                .status(HttpStatus.CONFLICT) // HTTP 409: Conflicto
                .body("El RUT o el correo electrónico ya se encuentran registrados.");

    } catch (Exception e) {
        // Captura cualquier otro error inesperado
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Ocurrió un error inesperado al registrar el usuario.");
    }
    }

    @PutMapping()
    @Operation(summary="Actualizar usuario",description = "Actualiza los datos de un usuario ya registrado")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Usuario actualizado",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Usuario.class))),
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<Usuario> actualizarUsuario(@RequestBody Usuario usuario){
        try {
            Usuario updUser = usuarioService.updateUsuario(usuario);
            if (updUser==null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(updUser);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary="Eliminar usuario",description = "Elimina un usuario mediante ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Usuario eliminado"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<Void> eliminarUsuario(@PathVariable("id")Integer id){
        try {
            usuarioService.deleteUsuario(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/login")
    @Operation(summary="Autenticar usuario", description = "Valida las credenciales de un usuario para iniciar sesión")
        public ResponseEntity<Usuario> login(@RequestBody LoginRequest loginRequest) {
        try {
        // Logs para debugging
        System.out.println("Email received: " + loginRequest.getEmail());
        System.out.println("Password received: " + loginRequest.getPassword());

        // 1. Busca un usuario por su email
        Usuario usuario = usuarioService.findByCorreo(loginRequest.getEmail()); // Puede que necesites crear este método en tu UsuarioService

        System.out.println("Usuario found: " + (usuario != null ? "Correo: " + usuario.getCorreo() + ", Contrasena: " + usuario.getContrasena() : "null"));

        // 2. Comprueba si el usuario existe y si la contraseña coincide
            if (usuario != null && usuario.getContrasena().equals(loginRequest.getPassword())) {
            // Si las credenciales son correctas, devuelve el usuario.
            return ResponseEntity.ok(usuario);
            } else {
            // Si el usuario no existe o la contraseña es incorrecta, devuelve un error 401 (No autorizado)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            } catch (Exception e) {
        return ResponseEntity.internalServerError().build();
        }
    }
}