package com.gestionlicencias.gestionlicenciasconducir.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestionlicencias.gestionlicenciasconducir.model.Licencia;

public interface LicenciaRepository extends JpaRepository<Licencia, Integer> {

    Optional<Licencia> findByIdLicencia(Integer idLicencia);
}
