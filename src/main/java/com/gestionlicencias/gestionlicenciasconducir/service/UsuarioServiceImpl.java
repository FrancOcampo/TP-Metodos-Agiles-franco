package com.gestionlicencias.gestionlicenciasconducir.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.gestionlicencias.gestionlicenciasconducir.dto.UsuarioRecord;
import com.gestionlicencias.gestionlicenciasconducir.model.Usuario;
import com.gestionlicencias.gestionlicenciasconducir.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, BCryptPasswordEncoder passwordEncoder) {
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
        usuario.setContrasena(passwordEncoder.encode(usuarioRecord.contrasena()));
        usuario.setRol("Administrativo"); 

        repository.save(usuario);
    }
}
