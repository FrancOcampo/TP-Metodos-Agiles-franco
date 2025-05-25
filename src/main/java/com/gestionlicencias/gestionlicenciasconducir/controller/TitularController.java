package com.gestionlicencias.gestionlicenciasconducir.controller;

import com.gestionlicencias.gestionlicenciasconducir.mapper.TitularMapper;
import com.gestionlicencias.gestionlicenciasconducir.model.Titular;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.gestionlicencias.gestionlicenciasconducir.service.TitularService;
import com.gestionlicencias.gestionlicenciasconducir.dto.TitularDTO;
import com.gestionlicencias.gestionlicenciasconducir.dto.TitularRecord;
import com.gestionlicencias.gestionlicenciasconducir.model.TipoDocumento;
import com.gestionlicencias.gestionlicenciasconducir.model.Titular;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/*Estas son anotaciones para la documentación de la API
 * Ingresar a http://localhost:8080/swagger-ui/index.html para ver la documentación de la API
*/
@Tag(name = "Titular Controller", description = "Operaciones para la gestión de titulares")
@Controller
@RequestMapping("/api/titulares")
public class TitularController {

    private final TitularService service;
    private final TitularMapper titularMapper;

    @Autowired
    public TitularController(TitularService service, TitularMapper titularMapper) {
        this.service = service;
        this.titularMapper = titularMapper;
    }

    @Operation(summary = "Registrar un titular", 
                description = "Registra un nuevo titular en la base de datos", 
                responses = {
                    @ApiResponse(responseCode = "201", description = "Titular registrado correctamente"),
                    @ApiResponse(responseCode = "400", description = "Error al registrar el titular"),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
                }) 

   @PostMapping("/registrar")
    public ResponseEntity<?> registrarTitular(@RequestBody @Valid TitularRecord titularRecord) {
        /*try{
            // Verificar si ya existe un titular con el mismo documento
            if (service.buscarTitular(titularRecord.tipoDocumento(), titularRecord.documento()) != null) {
                throw new IllegalArgumentException(
                        "Ya existe un titular con documento: " + titularRecord.documento() + " y tipo de documento: " + titularRecord.tipoDocumento()
                );
            }
            Titular titular = service.registrarTitular(titularRecord);

            return ResponseEntity.status(HttpStatus.CREATED).body(titular);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }*/

        //sugerencia sacar:
        service.registrarTitular(titularRecord);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    //Falta hacer un get de Titular por tipo de documento y numero de documento
    
    //Endpoints para el front

    @GetMapping
    public String mostrarMenu() {  return "menuTitular";    }

    @Operation(
            summary = "Buscar un Titular",
            description = "Busca un titular por dtipo y numero de documento",
            responses = {
                    @ApiResponse(responseCode = "202", description = "Titular no encontrado"),
                    @ApiResponse(responseCode = "404", description = "Titular no encontrado"),
                    @ApiResponse(responseCode = "400", description = "Parámetros Inválidos")
            }
    )
    @GetMapping("/buscar/titular")
    public ResponseEntity<TitularRecord> buscarTitular(
            @RequestParam TipoDocumento tipoDocumento,
            @RequestParam String documento
    ){
        try{
            TitularRecord titularRecord = service.buscarTitular(tipoDocumento, documento);
            return ResponseEntity.ok(titularRecord);
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            //Alternativa
            //return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

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
                                // sugerencia: model.addAttribute("bindingResult", bindingResult);
                                return "registroTitular";
                            }

                            try {
                                //java.sql.Date fechaNacimientoSql = new java.sql.Date(titularDTO.getFechaNacimiento().getTime());

                                TitularRecord dto = titularMapper.toRecord(titularDTO);
                                /*
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
                                */
                        /*
                                service.registrarTitular(dto);
                                redirectAttributes.addFlashAttribute("successMessage", "Titular registrado exitosamente.");

                                // Path construido dinamicamente, aumenta flexibilidad ante cambios del endpoint o entornos donde no se usa '/'
                                String redirectPath = ServletUriComponentsBuilder.fromCurrentContextPath()
                                        .path("/api/titulares/registroTitular")
                                        .toUriString();

                                return "redirect:" + redirectPath;

                            } catch (IllegalArgumentException e) {
                                model.addAttribute("errorMessage", e.getMessage());
                                return "registroTitular";

                            } catch (Exception e) {
                                //sugerencia: logger.error("Error mientras se registraba un titular", e);
                                model.addAttribute("errorMessage", "Ocurrió un error al registrar el titular.");
                                return "registroTitular";
                            }
                        }
                        */

}
