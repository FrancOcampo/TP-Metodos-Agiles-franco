package com.gestionlicencias.gestionlicenciasconducir.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/licencias")
public class LicenciaController {

    @GetMapping("/emitirLicencia")
    public String mostrarFormulario() {   return "emisionLicencia";    }
}
