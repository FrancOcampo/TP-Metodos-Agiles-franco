package com.gestionlicencias.gestionlicenciasconducir.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.gestionlicencias.gestionlicenciasconducir.model.Licencia;
import com.gestionlicencias.gestionlicenciasconducir.model.Titular;

public interface LicenciaRepository extends JpaRepository<Licencia, Integer> {

    Optional<Licencia> findByIdLicencia(Integer idLicencia);
    Licencia findFirstByTitularOrderByFechaInicioDesc(Titular titular);
    List<Licencia> findByFechaVencimientoAfter(LocalDate fecha);
}
