package com.gestionlicencias.gestionlicenciasconducir.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gestionlicencias.gestionlicenciasconducir.dto.UsuarioRecord;
import com.gestionlicencias.gestionlicenciasconducir.model.TipoDocumento;
import com.gestionlicencias.gestionlicenciasconducir.service.UsuarioService;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Usuario Controller", description = "Operaciones para la gestión de usuarios")
@Controller
@RequestMapping("/api/usuarios")
public class UsuarioController {


    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }
    
    @GetMapping("/menuOpcionesAdministrativo") 
    String mostrarMenuOpcionesAdministrativo(){
        return "menuOpcionesUsuarioAdministrativo";
    }

    @GetMapping("/menuOpcionesAdministrador") 
    String mostrarMenuOpcionesAdministrador(){
        return "menuOpcionesUsuarioAdministrador";
    }

    @GetMapping("/menuUsuario") 
    String mostrarMenuUsuario() {
        return "menuUsuarioAdmin";
    }

    @GetMapping("/registroUsuarioAdministrativo")
    String mostrarRegistroUsuarioAdministrativo() {
        return "registroUsuario";
    }

    @GetMapping("/login") 
    String mostrarLogin() {
        return "login";
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarUsuario(@RequestBody UsuarioRecord usuarioRecord) {
        try {
            usuarioService.registrarUsuario(usuarioRecord);
            return ResponseEntity.ok(Map.of("message", "Usuario registrado correctamente"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("message", "Ocurrió un error al registrar el usuario"));
        }
    }

    @Operation(
        summary = "Buscar un Usuario",
        description = "Busca un usuario por tipo de documento, número de documento y nombre de usuario. Todos los parámetros son opcionales.",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Usuarios encontrados"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Parámetros inválidos"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno del servidor")
        }
    )
    @GetMapping("/buscar")
    public ResponseEntity<List<UsuarioRecord>> buscarUsuario(
        @RequestParam(required = false) TipoDocumento tipoDocumento, 
        @RequestParam(required = false) String documento, 
        @RequestParam(required = false) String nombreUsuario) {
        try{
            List<UsuarioRecord> usuarios = usuarioService.buscarUsuario(tipoDocumento, documento, nombreUsuario);
            return ResponseEntity.ok(usuarios);
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(
        summary = "Modificar un Usuario",
        description = "Modifica los datos de un usuario existente.",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Usuario modificado correctamente"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno del servidor")
        }
    )
    @PutMapping("/modificar")
    public ResponseEntity<?> modificarUsuario(@RequestBody UsuarioRecord usuarioRecord) {
        try {
            usuarioService.modificarUsuario(usuarioRecord);
            return ResponseEntity.ok(Map.of("message", "Usuario modificado correctamente"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("message", "Ocurrió un error al modificar el usuario"));
        }
    }

}


