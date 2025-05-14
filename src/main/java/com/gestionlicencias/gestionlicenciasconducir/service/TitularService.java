package com.gestionlicencias.gestionlicenciasconducir.service;

import java.util.List;

import com.gestionlicencias.gestionlicenciasconducir.model.Titular;
import com.gestionlicencias.gestionlicenciasconducir.Exception.ExisteDocumentoException;

public interface TitularService {
    Titular registrarTitular(Titular titular) throws ExisteDocumentoException;
    List<Titular> listarTitulares();
    // …otros métodos: buscarPorId, eliminar, etc.
}
