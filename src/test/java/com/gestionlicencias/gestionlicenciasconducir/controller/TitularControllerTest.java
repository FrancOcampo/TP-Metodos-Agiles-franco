package com.gestionlicencias.gestionlicenciasconducir.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestionlicencias.gestionlicenciasconducir.dto.TitularRecord;
import com.gestionlicencias.gestionlicenciasconducir.mapper.TitularMapper;
import com.gestionlicencias.gestionlicenciasconducir.model.TipoDocumento;
import com.gestionlicencias.gestionlicenciasconducir.model.Titular;
import com.gestionlicencias.gestionlicenciasconducir.service.TitularService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest(TitularController.class)
@ExtendWith(MockitoExtension.class)
class TitularControllerTest {

    //@Autowired
    private MockMvc mockMvc;

    //@Autowired
    private ObjectMapper objectMapper;

    @Mock
    private TitularService titularService;

    @Mock
    private TitularMapper titularMapper;

    @InjectMocks
    private TitularController titularController;

    private TitularRecord titularValido;
    private Titular titularEsperado;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(titularController).build();

        objectMapper = new ObjectMapper();

        titularValido = new TitularRecord(
            TipoDocumento.DNI,
            "12345678",
            "Juan",
            "Pérez",
            Date.from(LocalDate.of(1990, 1, 1).atStartOfDay().toInstant(java.time.ZoneOffset.UTC)),
            "Calle Principal 123",
            "A",
            "Positivo",
            true
        );
        //titularEsperado = titularValido.toTitular();
        titularEsperado = new Titular();
        titularEsperado.setTipoDocumento(TipoDocumento.DNI);
        titularEsperado.setDocumento("12345678");
        titularEsperado.setNombre("Juan");
        titularEsperado.setApellido("Pérez");
        titularEsperado.setFechaNacimiento(titularValido.fechaNacimiento());
        titularEsperado.setDireccion("Calle Principal 123");
        titularEsperado.setGrupoSanguineo("A");
        titularEsperado.setFactorRH("Positivo");
        titularEsperado.setDonanteOrganos(true);

        // Mock the behavior of the mapper
        //when(titularMapper.toEntity(any(TitularRecord.class))).thenReturn(titularEsperado);
        //when(titularMapper.toRecord(any(Titular.class))).thenReturn(titularValido);
    }

    @Test
    void testBuscarTitular_Exitoso() throws Exception {
        // Arrange
        when(titularService.buscarTitular(TipoDocumento.DNI, "12345678"))
                .thenReturn(titularValido);

        // Act & Assert
        mockMvc.perform(get("/api/titulares/buscar/titular")
                        .param("tipoDocumento", TipoDocumento.DNI.name())
                        .param("documento", "12345678")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testBuscarTitular_NoEncontrado() throws Exception {
        // Arrange
        when(titularService.buscarTitular(TipoDocumento.DNI, "12345678"))
                .thenThrow(new IllegalArgumentException("Titular no encontrado"));

        // Act & Assert
        mockMvc.perform(get("/api/titulares/buscar/titular")
                        .param("tipoDocumento", TipoDocumento.DNI.name())
                        .param("documento", "12345678")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testRegistrarTitular_Exitoso() throws Exception {
        // Arrange
        when(titularService.registrarTitular(any(TitularRecord.class)))
            .thenReturn(titularEsperado);

        // Act & Assert
        mockMvc.perform(post("/api/titulares/registrar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(titularValido)))
                .andExpect(status().isCreated());
    }

    @Test
    void testRegistrarTitular_DocumentoExistente() throws Exception {
        // Arrange
        when(titularService.registrarTitular(any(TitularRecord.class)))
            .thenThrow(new IllegalArgumentException("Ya existe un titular con documento: " + titularValido.documento()));

        // Act & Assert
        mockMvc.perform(post("/api/titulares/registrar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(titularValido)))
                .andExpect(status().isBadRequest());
    }

}
