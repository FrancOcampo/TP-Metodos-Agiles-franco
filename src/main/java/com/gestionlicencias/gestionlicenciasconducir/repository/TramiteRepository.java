package com.gestionlicencias.gestionlicenciasconducir.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.gestionlicencias.gestionlicenciasconducir.model.Titular;
import com.gestionlicencias.gestionlicenciasconducir.model.Tramite;

public interface TramiteRepository extends JpaRepository<Tramite, Integer> {
    
    Tramite findFirstByTitularAsociadoOrderByFechaDesc(Titular titular);
}
