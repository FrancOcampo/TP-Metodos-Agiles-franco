package com.gestionlicencias.gestionlicenciasconducir.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.gestionlicencias.gestionlicenciasconducir.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Usuario findByIdUsuario(Integer idUsuario);

}
