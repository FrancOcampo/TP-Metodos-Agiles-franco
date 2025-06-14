package com.gestionlicencias.gestionlicenciasconducir.service;

import java.util.List;
import java.util.Date;
import com.gestionlicencias.gestionlicenciasconducir.dto.LicenciaListadoRecord;
import com.gestionlicencias.gestionlicenciasconducir.dto.LicenciaRecord;
import com.gestionlicencias.gestionlicenciasconducir.model.Licencia;
import com.gestionlicencias.gestionlicenciasconducir.model.Titular;

public interface LicenciaService {
    Float calcularCostoLicencia(String clase, Integer vigencia);
    public Licencia obtenerUltimaLicenciaTitular(Titular titular);
    List<LicenciaRecord> buscarLicenciasVigentes(String nombreApellido, String grupoSanguineo, String factorRH, boolean donanteOrganos);
    List<LicenciaListadoRecord> buscarLicenciasNoVigentes(Date fechaDesde, Date fechaHasta, String clase);
}
