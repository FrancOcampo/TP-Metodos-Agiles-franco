package com.gestionlicencias.gestionlicenciasconducir.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.gestionlicencias.gestionlicenciasconducir.model.Titular;
import com.gestionlicencias.gestionlicenciasconducir.service.TitularService;
import com.gestionlicencias.gestionlicenciasconducir.Exception.ExisteDocumentoException;

@Controller
@RequestMapping("/api/titulares")
public class TitularController {

    private final TitularService service;

    @Autowired
    public TitularController(TitularService service) {
        this.service = service;
    }

    @PostMapping("/registrar")
    public ResponseEntity<Titular> registrarTitular(@RequestBody Titular titular) {
        try {
            Titular creado = service.registrarTitular(titular);
            return ResponseEntity.ok(creado);
        } catch (ExisteDocumentoException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    
    //Endpoints para el front

    @GetMapping
    public String mostrarMenu() {  return "menuTitular";    }

    @GetMapping("/registroTitular")
    public String mostrarFormulario() {   return "registroTitular";    }

    @GetMapping("/modificar")
    public String mostrarFormularioModificar() {   return "modificarTitular";    }

    //eliminar
    @GetMapping("/eliminar")
    public String mostrarFormularioEliminar() {   return "eliminarTitular";    }

    //listar
    @GetMapping("/listar")
    public String mostrarFormularioListar() {   return "listarTitular";    }

}
