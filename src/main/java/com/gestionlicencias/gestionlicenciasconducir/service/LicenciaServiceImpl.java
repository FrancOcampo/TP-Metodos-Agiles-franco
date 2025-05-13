package com.gestionlicencias.gestionlicenciasconducir.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.gestionlicencias.gestionlicenciasconducir.Exception.ClaseVigenciaInvalidaException;

@Service
public class LicenciaServiceImpl implements LicenciaService {

    private static final List<String> CLASES_VALIDAS = Arrays.asList("A", "B", "C", "E", "G");
    private static final List<Integer> VIGENCIAS_VALIDAS = Arrays.asList(1, 3, 4, 5);

    @Override
    public Float calcularCostoLicencia(String clase, Integer vigencia) throws ClaseVigenciaInvalidaException {
        // Validar valores nulos
        if (clase == null) {
            throw new ClaseVigenciaInvalidaException("Clase de licencia no válida. Las clases válidas son: A, B, C, E, G");
        }
        if (vigencia == null) {
            throw new ClaseVigenciaInvalidaException("Vigencia no válida. Las vigencias válidas son: 1, 3, 4, 5 años");
        }

        // Validar clase
        if (!CLASES_VALIDAS.contains(clase.toUpperCase())) {
            throw new ClaseVigenciaInvalidaException("Clase de licencia no válida. Las clases válidas son: A, B, C, E, G");
        }

        // Validar vigencia
        if (!VIGENCIAS_VALIDAS.contains(vigencia)) {
            throw new ClaseVigenciaInvalidaException("Vigencia no válida. Las vigencias válidas son: 1, 3, 4, 5 años");
        }

        Float costo = 8.00f;
        
        switch (clase.toUpperCase()) {
            case "A":
                if (vigencia == 5) costo += 40;
                else if (vigencia == 4) costo += 30;
                else if (vigencia == 3) costo += 25;
                else if (vigencia == 1) costo += 20;
                break;
            case "B":
                if (vigencia == 5) costo += 40;
                else if (vigencia == 4) costo += 30;
                else if (vigencia == 3) costo += 25;
                else if (vigencia == 1) costo += 20;
                break;
            case "C":
                if (vigencia == 5) costo += 47;
                else if (vigencia == 4) costo += 35;
                else if (vigencia == 3) costo += 30;
                else if (vigencia == 1) costo += 23;
                break;
            case "E":
                if (vigencia == 5) costo += 59;
                else if (vigencia == 4) costo += 44;
                else if (vigencia == 3) costo += 39;
                else if (vigencia == 1) costo += 29;
                break;
            case "G":
                if (vigencia == 5) costo += 40;
                else if (vigencia == 4) costo += 30;
                else if (vigencia == 3) costo += 25;
                else if (vigencia == 1) costo += 20;
                break;
        }

        return costo;
    }

}
