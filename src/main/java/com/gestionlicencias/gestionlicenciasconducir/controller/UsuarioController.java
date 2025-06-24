package com.gestionlicencias.gestionlicenciasconducir.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gestionlicencias.gestionlicenciasconducir.dto.UsuarioRecord;
import com.gestionlicencias.gestionlicenciasconducir.service.UsuarioService;

import org.springframework.web.bind.annotation.RequestBody;
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

}


