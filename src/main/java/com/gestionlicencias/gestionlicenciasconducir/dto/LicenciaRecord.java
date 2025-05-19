package com.gestionlicencias.gestionlicenciasconducir.dto;

import java.util.Date;
import com.gestionlicencias.gestionlicenciasconducir.model.Licencia;

public record LicenciaRecord(
    Integer idLicencia,
    String clase,
    Date fechaInicio,
    Date fechaVencimiento,
    Boolean estaVigente,
    String observaciones,
    TitularRecord titular
) {
    public Licencia toLicencia() {
        Licencia licencia = new Licencia();
        licencia.setIdLicencia(this.idLicencia);
        licencia.setClase(this.clase);
        licencia.setFechaInicio(this.fechaInicio);
        licencia.setFechaVencimiento(this.fechaVencimiento);
        licencia.setEstaVigente(this.estaVigente);
        licencia.setObservaciones(this.observaciones);
        licencia.setTitular(this.titular.toTitular());
        return licencia;
    }
}
