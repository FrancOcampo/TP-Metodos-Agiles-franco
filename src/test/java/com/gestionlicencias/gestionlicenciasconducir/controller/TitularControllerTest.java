package com.gestionlicencias.gestionlicenciasconducir.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestionlicencias.gestionlicenciasconducir.dto.TitularRecord;
import com.gestionlicencias.gestionlicenciasconducir.model.Titular;
import com.gestionlicencias.gestionlicenciasconducir.model.TipoDocumento;
import com.gestionlicencias.gestionlicenciasconducir.service.TitularService;

@WebMvcTest(TitularController.class)
class TitularControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TitularService titularService;

    private TitularRecord titularValido;
    private Titular titularEsperado;

    @BeforeEach
    void setUp() {
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
        titularEsperado = titularValido.toTitular();
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
