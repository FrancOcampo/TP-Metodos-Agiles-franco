package com.gestionlicencias.gestionlicenciasconducir.dto;

import java.util.Date;

public record LicenciaListadoRecord(
    String nombreCompletoTitular,
    Integer numeroLicencia,
    String clase,
    String estadoActual,
    Date fechaVencimiento
) {

}
