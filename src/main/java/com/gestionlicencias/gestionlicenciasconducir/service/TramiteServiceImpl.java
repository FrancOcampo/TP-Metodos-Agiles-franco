package com.gestionlicencias.gestionlicenciasconducir.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestionlicencias.gestionlicenciasconducir.model.Tramite;
import com.gestionlicencias.gestionlicenciasconducir.repository.TramiteRepository;

@Service
public class TramiteServiceImpl implements TramiteService {

    private final TramiteRepository repository;

    @Autowired
    public TramiteServiceImpl(TramiteRepository repository) {
        this.repository = repository;
    }

    @Override
    public Tramite registrarTramite(Tramite tramite) {
        return repository.save(tramite);
    }

}
