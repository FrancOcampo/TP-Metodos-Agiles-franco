package com.gestionlicencias.gestionlicenciasconducir.Exception;

public class ExisteDocumentoException extends Exception {
    
    public ExisteDocumentoException() {
        super();
    }

    public ExisteDocumentoException(String message) {
        super(message);
    }

    public ExisteDocumentoException(String message, Throwable cause) {
        super(message, cause);
    }
}
