package com.gestionlicencias.gestionlicenciasconducir.Exception;

public class ClaseEmisionInvalidaException extends Exception {

    public ClaseEmisionInvalidaException(String mensaje) {
        super(mensaje);
    }

    public ClaseEmisionInvalidaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
