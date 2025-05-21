package com.gestionlicencias.gestionlicenciasconducir.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gestionlicencias.gestionlicenciasconducir.model.Titular;
import com.gestionlicencias.gestionlicenciasconducir.repository.TitularRepository;
import com.gestionlicencias.gestionlicenciasconducir.dto.TitularRecord;

@Service
public class TitularServiceImpl implements TitularService {

    private final TitularRepository repository;

    @Autowired
    public TitularServiceImpl(TitularRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public Titular registrarTitular(TitularRecord titularRecord) {
        // Verificar si ya existe un titular con el mismo documento
        if (repository.existsByTipoDocumentoAndDocumento(titularRecord.getTipoDocumento(), titularRecord.getDocumento())) {
            throw new IllegalArgumentException("Ya existe un titular con documento: " + titularRecord.getDocumento() + " y tipo de documento: " + titularRecord.getTipoDocumento());
        }
        // Convertir el DTO a entidad
        Titular titular = titularRecord.toTitular();
        // Guardar el titular en la base de datos
        return repository.save(titular);
    }

    @Override
    public List<Titular> listarTitulares() {
        return repository.findAll();
    }

}
