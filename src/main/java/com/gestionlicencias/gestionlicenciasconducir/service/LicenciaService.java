package com.gestionlicencias.gestionlicenciasconducir.service;

import com.gestionlicencias.gestionlicenciasconducir.model.Licencia;
import com.gestionlicencias.gestionlicenciasconducir.model.Titular;

public interface LicenciaService {
    Float calcularCostoLicencia(String clase, Integer vigencia);
    public Licencia obtenerUltimaLicenciaTitular(Titular titular);
}
