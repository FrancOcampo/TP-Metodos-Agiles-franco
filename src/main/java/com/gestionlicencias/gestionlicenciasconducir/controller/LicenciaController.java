package com.gestionlicencias.gestionlicenciasconducir.controller;

import com.gestionlicencias.gestionlicenciasconducir.Exception.ClaseEmisionInvalidaException;
import com.gestionlicencias.gestionlicenciasconducir.dto.LicenciaRecord;
import com.gestionlicencias.gestionlicenciasconducir.model.Titular;
import com.gestionlicencias.gestionlicenciasconducir.service.LicenciaServiceImpl;
import com.gestionlicencias.gestionlicenciasconducir.service.TitularServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Licencia Controller", description = "Operaciones para la emisión de licencias")
@Controller
@RequestMapping("/api/licencias")
public class LicenciaController {

    private final LicenciaServiceImpl licenciaService;
    private final TitularServiceImpl titularService;;

    @Autowired
    public LicenciaController(LicenciaServiceImpl licenciaService, TitularServiceImpl titularService) {
        this.licenciaService = licenciaService;
        this.titularService = titularService;
    }

    @GetMapping("/registroLicencia")
    public String mostrarFormulario() {  return "emisionLicencia";    }

    @Operation(
        summary = "Emitir una licencia",
        description = "Registra una nueva licencia para un titular",
        responses = {
            @ApiResponse(responseCode = "201", description = "Licencia emitida correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o clase no permitida"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
        }
    )
    
    @PostMapping("/registrar")
    public ResponseEntity<?> registrarLicencia(@RequestBody @Valid LicenciaRecord licenciaRecord) {
        try {
            Titular titular = titularService.buscarTitularDocumento(
                licenciaRecord.titular().tipoDocumento(),
                licenciaRecord.titular().documento()
            );

            if (titular == null) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Titular no encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            }

            licenciaService.emitirLicencia(
                titular,
                licenciaRecord.clase(),
                licenciaRecord.observaciones()
            );

            return new ResponseEntity<>(HttpStatus.CREATED);

        } catch (ClaseEmisionInvalidaException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", "Error: " + e.getMessage()));
                
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Error inesperado: " + e.getMessage()));
        }
    }

    

}
