package com.gestionlicencias.gestionlicenciasconducir.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.gestionlicencias.gestionlicenciasconducir.dto.UsuarioRecord;
import com.gestionlicencias.gestionlicenciasconducir.model.TipoDocumento;
import com.gestionlicencias.gestionlicenciasconducir.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    @Test
    void registrarUsuario_nombreUsuarioYaExiste_lanzaException() {
        UsuarioRecord usuarioRecord = new UsuarioRecord("admin", TipoDocumento.DNI, "12345678", "Perez", "Juan", "clave123");

        when(repository.existsByNombreUsuario("admin")).thenReturn(true);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
            () -> usuarioService.registrarUsuario(usuarioRecord));

        assertEquals("El nombre de usuario ya está registrado.", ex.getMessage());
    }

    @Test
    void registrarUsuario_datosValidos_guardarUsuario() {
        UsuarioRecord usuarioRecord = new UsuarioRecord("admin", TipoDocumento.DNI, "12345678", "Perez", "Juan", "clave123");

        when(repository.existsByNombreUsuario("admin")).thenReturn(false);
        when(passwordEncoder.encode("clave123")).thenReturn("hashedPassword");

        usuarioService.registrarUsuario(usuarioRecord);

        verify(repository, times(1)).save(argThat(usuario -> 
            usuario.getNombreUsuario().equals("admin") &&
            usuario.getDocumento().equals("12345678") &&
            usuario.getApellido().equals("Perez") &&
            usuario.getNombre().equals("Juan") &&
            usuario.getContrasena().equals("hashedPassword") &&
            usuario.getRol().equals("Administrativo")
        ));
    }

    @Test
    void buscarUsuario_todosNulos_devuelveTodos() {
        var usuario1 = new com.gestionlicencias.gestionlicenciasconducir.model.Usuario();
        usuario1.setNombreUsuario("admin");
        usuario1.setTipoDocumento(TipoDocumento.DNI);
        usuario1.setDocumento("12345678");
        usuario1.setApellido("Perez");
        usuario1.setNombre("Juan");
        usuario1.setContrasena("clave123");
        usuario1.setRol("Administrativo");

        var usuario2 = new com.gestionlicencias.gestionlicenciasconducir.model.Usuario();
        usuario2.setNombreUsuario("ana01");
        usuario2.setTipoDocumento(TipoDocumento.DNI);
        usuario2.setDocumento("87654321");
        usuario2.setApellido("Gomez");
        usuario2.setNombre("Ana");
        usuario2.setContrasena("clave456");
        usuario2.setRol("Administrativo");

        when(repository.buscarPorFiltros(null, null, null)).thenReturn(java.util.List.of(usuario1, usuario2));

        var result = usuarioService.buscarUsuario(null, null, null);
        assertEquals(2, result.size());
        assertEquals("admin", result.get(0).nombreUsuario());
        assertEquals("ana01", result.get(1).nombreUsuario());
    }

    @Test
    void buscarUsuario_porDocumento_devuelveUno() {
        var usuario = new com.gestionlicencias.gestionlicenciasconducir.model.Usuario();
        usuario.setNombreUsuario("admin");
        usuario.setTipoDocumento(TipoDocumento.DNI);
        usuario.setDocumento("12345678");
        usuario.setApellido("Perez");
        usuario.setNombre("Juan");
        usuario.setContrasena("clave123");
        usuario.setRol("Administrativo");

        when(repository.buscarPorFiltros(null, "12345678", null)).thenReturn(java.util.List.of(usuario));

        var result = usuarioService.buscarUsuario(null, "12345678", null);
        assertEquals(1, result.size());
        assertEquals("admin", result.get(0).nombreUsuario());
        assertEquals("12345678", result.get(0).documento());
    }

    @Test
    void buscarUsuario_porNombreUsuario_devuelveUno() {
        var usuario = new com.gestionlicencias.gestionlicenciasconducir.model.Usuario();
        usuario.setNombreUsuario("ana01");
        usuario.setTipoDocumento(TipoDocumento.DNI);
        usuario.setDocumento("87654321");
        usuario.setApellido("Gomez");
        usuario.setNombre("Ana");
        usuario.setContrasena("clave456");
        usuario.setRol("Administrativo");

        when(repository.buscarPorFiltros(null, null, "ana01")).thenReturn(java.util.List.of(usuario));

        var result = usuarioService.buscarUsuario(null, null, "ana01");
        assertEquals(1, result.size());
        assertEquals("ana01", result.get(0).nombreUsuario());
        assertEquals("87654321", result.get(0).documento());
    }

    @Test
    void buscarUsuario_combinado_devuelveUno() {
        var usuario = new com.gestionlicencias.gestionlicenciasconducir.model.Usuario();
        usuario.setNombreUsuario("admin");
        usuario.setTipoDocumento(TipoDocumento.DNI);
        usuario.setDocumento("12345678");
        usuario.setApellido("Perez");
        usuario.setNombre("Juan");
        usuario.setContrasena("clave123");
        usuario.setRol("Administrativo");

        when(repository.buscarPorFiltros(TipoDocumento.DNI, "12345678", "admin")).thenReturn(java.util.List.of(usuario));

        var result = usuarioService.buscarUsuario(TipoDocumento.DNI, "12345678", "admin");
        assertEquals(1, result.size());
        assertEquals("admin", result.get(0).nombreUsuario());
        assertEquals("12345678", result.get(0).documento());
    }

    @Test
    void modificarUsuario_usuarioExistente_modificaDatos() {
        UsuarioRecord usuarioRecord = new UsuarioRecord("admin", TipoDocumento.DNI, "12345678", "NuevoApellido", "NuevoNombre", "nuevaClave");
        com.gestionlicencias.gestionlicenciasconducir.model.Usuario usuario = new com.gestionlicencias.gestionlicenciasconducir.model.Usuario();
        usuario.setNombreUsuario("admin");
        usuario.setTipoDocumento(TipoDocumento.DNI);
        usuario.setDocumento("12345678");
        usuario.setApellido("ViejoApellido");
        usuario.setNombre("ViejoNombre");
        usuario.setContrasena("viejaClave");
        usuario.setRol("Administrativo");

        when(repository.findByTipoDocumentoAndDocumento(TipoDocumento.DNI, "12345678")).thenReturn(usuario);
        when(passwordEncoder.encode("nuevaClave")).thenReturn("hashedNuevaClave");

        usuarioService.modificarUsuario(usuarioRecord);

        assertEquals("NuevoApellido", usuario.getApellido());
        assertEquals("NuevoNombre", usuario.getNombre());
        assertEquals("hashedNuevaClave", usuario.getContrasena());
        verify(repository).save(usuario);
    }

    @Test
    void modificarUsuario_usuarioNoExiste_lanzaException() {
        UsuarioRecord usuarioRecord = new UsuarioRecord("admin", TipoDocumento.DNI, "12345678", "Apellido", "Nombre", "clave");
        when(repository.findByTipoDocumentoAndDocumento(TipoDocumento.DNI, "12345678")).thenReturn(null);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
            () -> usuarioService.modificarUsuario(usuarioRecord));
        assertEquals("Usuario no encontrado.", ex.getMessage());
        verify(repository, never()).save(any());
    }
}
