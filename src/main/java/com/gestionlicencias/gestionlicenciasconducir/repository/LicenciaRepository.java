package com.gestionlicencias.gestionlicenciasconducir.repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.gestionlicencias.gestionlicenciasconducir.model.Licencia;
import com.gestionlicencias.gestionlicenciasconducir.model.Titular;

public interface LicenciaRepository extends JpaRepository<Licencia, Integer> {

    Optional<Licencia> findByIdLicencia(Integer idLicencia);
    Licencia findFirstByTitularOrderByFechaInicioDesc(Titular titular);
    List<Licencia> findByFechaVencimientoAfter(LocalDate fecha);
    
    @Query("SELECT l FROM Licencia l WHERE l.fechaVencimiento < :hoy " +
           "AND (:clase IS NULL OR l.clase = :clase) " +
           "AND NOT EXISTS (SELECT l2 FROM Licencia l2 WHERE l2.titular = l.titular " +
           "AND l2.fechaVencimiento > :hoy)")
    List<Licencia> findLicenciasNoVigentes(
        @Param("hoy") Date hoy,
        @Param("clase") String clase
    );
}
