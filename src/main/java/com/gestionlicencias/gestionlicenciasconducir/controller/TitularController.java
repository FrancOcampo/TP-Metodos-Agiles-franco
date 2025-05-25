package com.gestionlicencias.gestionlicenciasconducir.controller;

import java.sql.Date;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.gestionlicencias.gestionlicenciasconducir.service.TitularService;
import com.gestionlicencias.gestionlicenciasconducir.dto.TitularDTO;
import com.gestionlicencias.gestionlicenciasconducir.dto.TitularRecord;
import com.gestionlicencias.gestionlicenciasconducir.model.TipoDocumento;

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

    //Falta hacer un get de Titular por tipo de documento y numero de documento
    
    //Endpoints para el front

    @GetMapping
    public String mostrarMenu() {  return "menuTitular";    }

    @GetMapping("/registroTitular")
    public String mostrarFormulario(Model model) {
        if (!model.containsAttribute("titularDTO")) {
            model.addAttribute("titularDTO", new TitularDTO());
        }
        return "registroTitular";
    }

    @GetMapping("/modificar")
    public String mostrarFormularioModificar() {   return "modificarTitular";    }

    //eliminar
    @GetMapping("/eliminar")
    public String mostrarFormularioEliminar() {   return "eliminarTitular";    }

    //listar
    @GetMapping("/listar")
    public String mostrarFormularioListar() {   return "listarTitular";    }

    //No se cualquier cosa esto
    /*
    @PostMapping("/registrar")
    public String registrarTitular(
        @Valid @ModelAttribute("titularDTO") TitularDTO titularDTO,
        BindingResult bindingResult,
        Model model,
        RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            // Si hay errores de validación, se vuelve al formulario con los datos cargados
            return "registroTitular";
        }

        try {
            java.sql.Date fechaNacimientoSql = new java.sql.Date(titularDTO.getFechaNacimiento().getTime());

            TitularRecord dto = new TitularRecord(
                titularDTO.getTipoDocumento(),
                titularDTO.getDocumento(),
                titularDTO.getNombre(),
                titularDTO.getApellido(), 
                fechaNacimientoSql,
                titularDTO.getDireccion(),
                titularDTO.getGrupoSanguineo(),
                titularDTO.getFactorRH(),
                titularDTO.getDonanteOrganos()
            );

            service.registrarTitular(dto);
            redirectAttributes.addFlashAttribute("successMessage", "Titular registrado exitosamente.");
            return "redirect:/api/titulares/registroTitular";

        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "registroTitular";
            
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Ocurrió un error al registrar el titular.");
            return "registroTitular";
        }
    }
    */
}
