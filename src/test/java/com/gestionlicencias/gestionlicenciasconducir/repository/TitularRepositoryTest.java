package com.gestionlicencias.gestionlicenciasconducir.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.gestionlicencias.gestionlicenciasconducir.model.Titular;
import com.gestionlicencias.gestionlicenciasconducir.model.TipoDocumento;

@DataJpaTest
public class TitularRepositoryTest {

    @Autowired
    private TitularRepository titularRepository;

    private Titular titular;

    @BeforeEach
    void setUp() {
        // Crear un titular de ejemplo para usar en los tests
        titular = new Titular();
        titular.setTipoDocumento(TipoDocumento.DNI);
        titular.setDocumento("12345678");
        titular.setNombre("Juan");
        titular.setApellido("Perez");
        titular.setFechaNacimiento(new java.util.Date());
        titular.setDireccion("Calle Falsa 123");
        titular.setGrupoSanguineo("O+");
        titular.setFactorRH("Positivo");
        titular.setDonanteOrganos(true);

        // Guardar el titular en la base de datos
        titularRepository.saveAndFlush(titular);
    }

    @Test
    void testExistsByTipoDocumentoAndDocumento() {
        // Verificar que el método existsByTipoDocumentoAndDocumento funciona correctamente
        boolean exists = titularRepository.existsByTipoDocumentoAndDocumento(TipoDocumento.DNI, "12345678");
        assertTrue(exists, "El titular con DNI 12345678 debería existir en la base de datos");

        boolean notExists = titularRepository.existsByTipoDocumentoAndDocumento(TipoDocumento.DNI, "87654321");
        assertFalse(notExists, "El titular con DNI 87654321 no debería existir en la base de datos");

        boolean notExistsDifferentType = titularRepository.existsByTipoDocumentoAndDocumento(TipoDocumento.PASAPORTE, "12345678");
        assertFalse(notExistsDifferentType, "No debería existir un titular con pasaporte 12345678");
    }

    @Test
    void testSaveAndRetrieveTitular() {
        // Verificar que se puede guardar y recuperar un titular correctamente
        Titular retrievedTitular = titularRepository.findById(titular.getIdTitular()).orElse(null);
        assertNotNull(retrievedTitular, "El titular debería haberse recuperado correctamente");
        assertEquals("Juan", retrievedTitular.getNombre(), "El nombre del titular debería ser 'Juan'");
        assertEquals("Perez", retrievedTitular.getApellido(), "El apellido del titular debería ser 'Perez'");
    }

    @Test
    void testUniqueConstraintOnDocumento() {
        // Verificar que no se pueden guardar dos titulares con el mismo documento
        Titular otroTitular = new Titular();
        otroTitular.setTipoDocumento(TipoDocumento.DNI);
        otroTitular.setDocumento("12345678"); // Documento duplicado
        otroTitular.setNombre("Maria");
        otroTitular.setApellido("Gomez");
        otroTitular.setFechaNacimiento(new java.util.Date());
        otroTitular.setDireccion("Calle Verdadera 456");
        otroTitular.setGrupoSanguineo("A+");
        otroTitular.setFactorRH("Negativo");
        otroTitular.setDonanteOrganos(false);

        Exception exception = assertThrows(Exception.class, () -> {
            titularRepository.save(otroTitular);
        });

        assertNotNull(exception, "Debería lanzarse una excepción al intentar guardar un documento duplicado");
    }
}
