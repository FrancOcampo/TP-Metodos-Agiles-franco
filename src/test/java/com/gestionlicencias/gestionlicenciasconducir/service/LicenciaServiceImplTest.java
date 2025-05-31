package com.gestionlicencias.gestionlicenciasconducir.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.gestionlicencias.gestionlicenciasconducir.repository.LicenciaRepository;

class LicenciaServiceImplTest {

    private LicenciaService licenciaService;
    private LicenciaRepository licenciaRepository;

    @BeforeEach
    void setUp() {
        licenciaService = new LicenciaServiceImpl(licenciaRepository);
    }

    @ParameterizedTest
    @CsvSource({
        "A, 5, 48.0",  // 8 + 40
        "A, 4, 38.0",  // 8 + 30
        "A, 3, 33.0",  // 8 + 25
        "A, 1, 28.0",  // 8 + 20
        "B, 5, 48.0",  // 8 + 40
        "B, 4, 38.0",  // 8 + 30
        "B, 3, 33.0",  // 8 + 25
        "B, 1, 28.0",  // 8 + 20
        "C, 5, 55.0",  // 8 + 47
        "C, 4, 43.0",  // 8 + 35
        "C, 3, 38.0",  // 8 + 30
        "C, 1, 31.0",  // 8 + 23
        "E, 5, 67.0",  // 8 + 59
        "E, 4, 52.0",  // 8 + 44
        "E, 3, 47.0",  // 8 + 39
        "E, 1, 37.0",  // 8 + 29
        "G, 5, 48.0",  // 8 + 40
        "G, 4, 38.0",  // 8 + 30
        "G, 3, 33.0",  // 8 + 25
        "G, 1, 28.0"   // 8 + 20
    })
    void calcularCostoLicencia_casoValido_retornaCostoEsperado(String clase, int vigencia, float costoEsperado) {
        float costoCalculado = licenciaService.calcularCostoLicencia(clase, vigencia);
        assertEquals(costoEsperado, costoCalculado, 0.01, 
            "El costo calculado para clase " + clase + " y vigencia " + vigencia + " años debe ser " + costoEsperado);
    }

    @Test
    void calcularCostoLicencia_claseInvalida_lanzaExcepcion() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> licenciaService.calcularCostoLicencia("X", 5),
            "Debería lanzar excepción para clase inválida"
        );

        assertTrue(exception.getMessage().contains("Clase de licencia no válida"));
    }

    @Test
    void calcularCostoLicencia_vigenciaInvalida_lanzaExcepcion() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> licenciaService.calcularCostoLicencia("A", 2),
            "Debería lanzar excepción para vigencia inválida"
        );

        assertTrue(exception.getMessage().contains("Vigencia no válida"));
    }

    @Test
    void calcularCostoLicencia_claseMinuscula_funcionaCorrectamente() {
        float costoCalculado = licenciaService.calcularCostoLicencia("a", 5);
        assertEquals(48.0, costoCalculado, 0.01, 
            "El costo debe ser calculado correctamente independientemente de mayúsculas/minúsculas");
    }

    @Test
    void calcularCostoLicencia_claseNula_lanzaExcepcion() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> licenciaService.calcularCostoLicencia(null, 5),
            "Debería lanzar excepción para clase nula"
        );

        assertTrue(exception.getMessage().contains("Clase de licencia no válida"));
    }

    @Test
    void calcularCostoLicencia_vigenciaNula_lanzaExcepcion() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> licenciaService.calcularCostoLicencia("A", null),
            "Debería lanzar excepción para vigencia nula"
        );

        assertTrue(exception.getMessage().contains("Vigencia no válida"));
    }
} 