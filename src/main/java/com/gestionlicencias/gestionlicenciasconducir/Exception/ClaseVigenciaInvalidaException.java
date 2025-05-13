package com.gestionlicencias.gestionlicenciasconducir.Exception;

public class ClaseVigenciaInvalidaException extends Exception {
    
    public ClaseVigenciaInvalidaException(String mensaje) {
        super(mensaje);
    }

    public ClaseVigenciaInvalidaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
} 