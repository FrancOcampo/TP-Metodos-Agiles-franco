package com.gestionlicencias.gestionlicenciasconducir.service;

import com.gestionlicencias.gestionlicenciasconducir.Exception.ClaseVigenciaInvalidaException;

public interface LicenciaService {
    Float calcularCostoLicencia(String clase, Integer vigencia) throws ClaseVigenciaInvalidaException;
}
