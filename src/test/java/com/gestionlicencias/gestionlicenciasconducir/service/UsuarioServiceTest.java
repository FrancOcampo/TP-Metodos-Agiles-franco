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
}
