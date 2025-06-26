package com.gestionlicencias.gestionlicenciasconducir.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.gestionlicencias.gestionlicenciasconducir.dto.UsuarioRecord;
import com.gestionlicencias.gestionlicenciasconducir.model.Usuario;
import com.gestionlicencias.gestionlicenciasconducir.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.repository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Usuario buscarUsuarioPorId(Integer idUsuario) {
        return repository.findByIdUsuario(idUsuario);
    }

    @Override
    public void registrarUsuario(UsuarioRecord usuarioRecord) {
        if (repository.existsByNombreUsuario(usuarioRecord.nombreUsuario())) {
            throw new IllegalArgumentException("El nombre de usuario ya está registrado.");
        }

        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(usuarioRecord.nombreUsuario());
        usuario.setTipoDocumento(usuarioRecord.tipoDocumento());
        usuario.setDocumento(usuarioRecord.documento());
        usuario.setApellido(usuarioRecord.apellido());
        usuario.setNombre(usuarioRecord.nombre());
        usuario.setContrasena(passwordEncoder.encode(usuarioRecord.contrasena()));
        usuario.setRol("Administrativo"); 

        repository.save(usuario);
    }
}


