package com.gestionlicencias.gestionlicenciasconducir.repository;

import org.springframework.stereotype.Repository;
import com.gestionlicencias.gestionlicenciasconducir.model.Licencia; // Adjust the package path if necessary
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface LicenciaRepository extends JpaRepository<Licencia, Integer> {
    //Busca una licencia por id
    Licencia findLicenciaById(Integer id);
}
