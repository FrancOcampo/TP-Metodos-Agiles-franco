package com.gestionlicencias.gestionlicenciasconducir.dto;

import java.util.Date;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Future;
import com.gestionlicencias.gestionlicenciasconducir.model.Licencia;
import org.hibernate.validator.constraints.Length; 

public record LicenciaRecord(
    @NotNull(message = "La clase de licencia es requerida")
    @Length(max = 1, message = "La clase de licencia debe tener como maximo 1 caracter")
    String clase,
    @NotNull(message = "La fecha de inicio es requerida")
    Date fechaInicio,
    @NotNull(message = "La fecha de vencimiento es requerida")
    @Future(message = "La fecha de vencimiento debe ser una fecha futura") 
    Date fechaVencimiento,
    @NotNull(message = "El estado de vigencia es requerido")
    Boolean estaVigente,
    @NotBlank(message = "Las observaciones son requeridas")
    @Length(max = 100, message = "Las observaciones deben tener como maximo 100 caracteres")
    String observaciones,
    @NotNull(message = "El titular es requerido")
    TitularRecord titular
) {
    public Licencia toLicencia() {
        Licencia licencia = new Licencia();
        licencia.setClase(this.clase);
        licencia.setFechaInicio(this.fechaInicio);
        licencia.setFechaVencimiento(this.fechaVencimiento);
        licencia.setEstaVigente(this.estaVigente);
        licencia.setObservaciones(this.observaciones);
        licencia.setTitular(this.titular.toTitular());
        return licencia;
    }
}
