package com.gestionlicencias.gestionlicenciasconducir.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Usuario Controller", description = "Operaciones para la gestión de usuarios")
@Controller
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @GetMapping("/menuOpcionesAdministrativo") 
    String mostrarMenuOpcionesAdministrativo(){
        return "menuOpcionesUsuarioAdministrativo";
    }

    @GetMapping("/menuOpcionesAdministrador") 
    String mostrarMenuOpcionesAdministrador(){
        return "menuOpcionesUsuarioAdministrador";
    }

}
