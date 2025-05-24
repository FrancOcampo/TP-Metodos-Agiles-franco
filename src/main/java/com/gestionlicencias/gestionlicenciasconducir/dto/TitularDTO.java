package com.gestionlicencias.gestionlicenciasconducir.dto;

import java.sql.Date;

import com.gestionlicencias.gestionlicenciasconducir.model.TipoDocumento;

public class TitularDTO {

    TipoDocumento tipoDocumento;
    String documento;
    String nombre;
    String apellido;
    Date fechaNacimiento;
    String direccion;
    String grupoSanguineo;
    String factorRH;
    Boolean donanteOrganos;
    
    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }
    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }
    public String getDocumento() {
        return documento;
    }
    public void setDocumento(String documento) {
        this.documento = documento;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getApellido() {
        return apellido;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }
    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public String getGrupoSanguineo() {
        return grupoSanguineo;
    }
    public void setGrupoSanguineo(String grupoSanguineo) {
        this.grupoSanguineo = grupoSanguineo;
    }
    public String getFactorRH() {
        return factorRH;
    }
    public void setFactorRH(String factorRH) {
        this.factorRH = factorRH;
    }
    public Boolean getDonanteOrganos() {
        return donanteOrganos;
    }
    public void setDonanteOrganos(Boolean donanteOrganos) {
        this.donanteOrganos = donanteOrganos;
    }

}