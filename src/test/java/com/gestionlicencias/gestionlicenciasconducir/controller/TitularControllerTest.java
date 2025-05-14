package com.gestionlicencias.gestionlicenciasconducir.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.gestionlicencias.gestionlicenciasconducir.model.Titular;
import com.gestionlicencias.gestionlicenciasconducir.service.TitularService;
import com.gestionlicencias.gestionlicenciasconducir.Exception.ExisteDocumentoException;

class TitularControllerTest {

    @Mock
    private TitularService titularService;

    @InjectMocks
    private TitularController titularController;

    private Titular titular;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        titular = new Titular();
        titular.setDocumento("12345678");
        titular.setNombre("Juan");
        titular.setApellido("Perez");
    }

    @Test
    void testRegistrarTitular_Exitoso() throws ExisteDocumentoException {
        // Arrange
        when(titularService.registrarTitular(titular)).thenReturn(titular);

        // Act
        ResponseEntity<Titular> response = titularController.registrarTitular(titular);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(titular, response.getBody());
        verify(titularService).registrarTitular(titular);
    }

    @Test
    void testRegistrarTitular_DocumentoExistente() throws ExisteDocumentoException {
        // Arrange
        when(titularService.registrarTitular(titular))
            .thenThrow(new ExisteDocumentoException("Ya existe un titular con documento " + titular.getDocumento()));

        // Act
        ResponseEntity<Titular> response = titularController.registrarTitular(titular);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        verify(titularService).registrarTitular(titular);
    }
}
