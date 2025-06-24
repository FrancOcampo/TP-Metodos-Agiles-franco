package com.gestionlicencias.gestionlicenciasconducir.service;

import com.gestionlicencias.gestionlicenciasconducir.dto.UsuarioRecord;
import com.gestionlicencias.gestionlicenciasconducir.model.Usuario;

public interface UsuarioService {

    public Usuario buscarUsuarioPorId(Integer idUsuario);
    public void registrarUsuario(UsuarioRecord usuarioRecord);
}
