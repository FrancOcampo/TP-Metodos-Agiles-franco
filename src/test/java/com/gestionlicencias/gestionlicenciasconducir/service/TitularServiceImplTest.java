package com.gestionlicencias.gestionlicenciasconducir.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Date;

import com.gestionlicencias.gestionlicenciasconducir.mapper.TitularMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gestionlicencias.gestionlicenciasconducir.dto.TitularRecord;
import com.gestionlicencias.gestionlicenciasconducir.model.Titular;
import com.gestionlicencias.gestionlicenciasconducir.model.TipoDocumento;
import com.gestionlicencias.gestionlicenciasconducir.repository.TitularRepository;

@ExtendWith(MockitoExtension.class)
class TitularServiceImplTest {

    @Mock
    private TitularRepository titularRepository;

    @Mock
    private TitularMapper titularMapper;

    private TitularService titularService;

    @BeforeEach
    void setUp() {
        titularService = new TitularServiceImpl(titularRepository, titularMapper);
    }

    @Test
    void testCrearTitularExitoso() {
        // Arrange
        TitularRecord titularRecord = new TitularRecord(
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

        //Titular titularEsperado = titularRecord.toTitular();
        Titular titularEsperado = new Titular();
            titularEsperado.setTipoDocumento(TipoDocumento.DNI);
            titularEsperado.setDocumento("12345678");
            titularEsperado.setNombre("Juan");
            titularEsperado.setApellido("Pérez");
            titularEsperado.setFechaNacimiento(Date.from(LocalDate.of(1990, 1, 1).atStartOfDay().toInstant(java.time.ZoneOffset.UTC)));
            titularEsperado.setDireccion("Calle Principal 123");
            titularEsperado.setGrupoSanguineo("A");
            titularEsperado.setFactorRH("Positivo");
            titularEsperado.setDonanteOrganos(true);

        when(titularMapper.toEntity(titularRecord)).thenReturn(titularEsperado);
        when(titularRepository.existsByTipoDocumentoAndDocumento(TipoDocumento.DNI, "12345678")).thenReturn(false);
        when(titularRepository.save(any(Titular.class))).thenReturn(titularEsperado);

        // Act
        Titular titularCreado = titularService.registrarTitular(titularRecord);

        // Assert
        assertNotNull(titularCreado);
        assertEquals(titularRecord.tipoDocumento(), titularCreado.getTipoDocumento());
        assertEquals(titularRecord.documento(), titularCreado.getDocumento());
        assertEquals(titularRecord.nombre(), titularCreado.getNombre());
        assertEquals(titularRecord.apellido(), titularCreado.getApellido());
        assertEquals(titularRecord.fechaNacimiento(), titularCreado.getFechaNacimiento());
        assertEquals(titularRecord.direccion(), titularCreado.getDireccion());
        assertEquals(titularRecord.grupoSanguineo(), titularCreado.getGrupoSanguineo());
        assertEquals(titularRecord.factorRH(), titularCreado.getFactorRH());
        assertEquals(titularRecord.donanteOrganos(), titularCreado.getDonanteOrganos());

        // Verify
        verify(titularMapper).toEntity(titularRecord);
        verify(titularRepository).existsByTipoDocumentoAndDocumento(TipoDocumento.DNI, "12345678");
        verify(titularRepository).save(titularEsperado);
    }

    @Test
    void testCrearTitularDocumentoDuplicado() {
        // Arrange
        TitularRecord titularRecord = new TitularRecord(
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

        when(titularRepository.existsByTipoDocumentoAndDocumento(TipoDocumento.DNI, "12345678")).thenReturn(true);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> titularService.registrarTitular(titularRecord),
            "Debería lanzar excepción cuando el documento ya existe"
        );

        assertTrue(exception.getMessage().contains("Ya existe un titular con documento: 12345678 y tipo de documento: DNI"));

        // Verify
        verify(titularRepository).existsByTipoDocumentoAndDocumento(TipoDocumento.DNI, "12345678");
        verify(titularRepository, never()).save(any(Titular.class));
    }
}
