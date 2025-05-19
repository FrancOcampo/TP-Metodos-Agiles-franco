package com.gestionlicencias.gestionlicenciasconducir.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.gestionlicencias.gestionlicenciasconducir.service.TitularService;
import com.gestionlicencias.gestionlicenciasconducir.dto.TitularRecord;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

/*Estas son anotaciones para la documentación de la API
 * Ingresar a http://localhost:8080/swagger-ui/index.html para ver la documentación de la API
*/
@Tag(name = "Titular Controller", description = "Operaciones para la gestión de titulares")
@Controller
@RequestMapping("/api/titulares")
public class TitularController {

    private final TitularService service;

    @Autowired
    public TitularController(TitularService service) {
        this.service = service;
    }

    @Operation(summary = "Registrar un titular", 
                description = "Registra un nuevo titular en la base de datos", 
                responses = {
                    @ApiResponse(responseCode = "201", description = "Titular registrado correctamente"),
                    @ApiResponse(responseCode = "400", description = "Error al registrar el titular"),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
                })
    @PostMapping("/registrar")
    public ResponseEntity<Void> registrarTitular(@RequestBody @Valid TitularRecord titularRecord) {
        service.registrarTitular(titularRecord);
        return new ResponseEntity<>(HttpStatus.CREATED);
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
