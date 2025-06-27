package com.gestionlicencias.gestionlicenciasconducir.repository;

import com.gestionlicencias.gestionlicenciasconducir.model.TipoDocumento;
import com.gestionlicencias.gestionlicenciasconducir.model.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;

@DataJpaTest
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @BeforeEach
    void setUp() {
        Usuario usuario1 = new Usuario();
        usuario1.setNombreUsuario("marcos05");
        usuario1.setTipoDocumento(TipoDocumento.DNI);
        usuario1.setDocumento("12345678");
        usuario1.setApellido("Perez");
        usuario1.setNombre("Marcos");
        usuario1.setContrasena("pass1");
        usuario1.setRol("Administrativo");
        usuarioRepository.save(usuario1);

        Usuario usuario2 = new Usuario();
        usuario2.setNombreUsuario("ana01");
        usuario2.setTipoDocumento(TipoDocumento.DNI);
        usuario2.setDocumento("87654321");
        usuario2.setApellido("Gomez");
        usuario2.setNombre("Ana");
        usuario2.setContrasena("pass2");
        usuario2.setRol("Administrativo");
        usuarioRepository.save(usuario2);
    }

    @Test
    void testBuscarPorFiltros_TodosNulos() {
        List<Usuario> usuarios = usuarioRepository.buscarPorFiltros(null, null, null);
        Assertions.assertEquals(2, usuarios.size());
    }

    @Test
    void testBuscarPorFiltros_PorTipoDocumento() {
        List<Usuario> usuarios = usuarioRepository.buscarPorFiltros(TipoDocumento.DNI, null, null);
        Assertions.assertEquals(2, usuarios.size());
    }

    @Test
    void testBuscarPorFiltros_PorDocumento() {
        List<Usuario> usuarios = usuarioRepository.buscarPorFiltros(null, "12345678", null);
        Assertions.assertEquals(1, usuarios.size());
        Assertions.assertEquals("marcos05", usuarios.get(0).getNombreUsuario());
    }

    @Test
    void testBuscarPorFiltros_PorNombreUsuario() {
        List<Usuario> usuarios = usuarioRepository.buscarPorFiltros(null, null, "ana01");
        Assertions.assertEquals(1, usuarios.size());
        Assertions.assertEquals("ana01", usuarios.get(0).getNombreUsuario());
    }

    @Test
    void testBuscarPorFiltros_Combinado() {
        List<Usuario> usuarios = usuarioRepository.buscarPorFiltros(TipoDocumento.DNI, "12345678", "marcos05");
        Assertions.assertEquals(1, usuarios.size());
        Assertions.assertEquals("marcos05", usuarios.get(0).getNombreUsuario());
    }
}
