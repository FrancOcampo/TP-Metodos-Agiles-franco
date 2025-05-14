package com.gestionlicencias.gestionlicenciasconducir.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.gestionlicencias.gestionlicenciasconducir.Exception.ClaseEmisionInvalidaException;
import com.gestionlicencias.gestionlicenciasconducir.Exception.ClaseVigenciaInvalidaException;
import com.gestionlicencias.gestionlicenciasconducir.model.Licencia;
import com.gestionlicencias.gestionlicenciasconducir.model.Titular;

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
    /*
    public Licencia emitirLicencia(Titular titular, String claseLicencia) throws ClaseEmisionInvalidaException {

        // Obtener las licencias del titular
        List<Licencia> licenciasTitular = titular.getLicencias();

        // Buscar si el titular tiene una licencia de tipo B
        boolean tieneLicenciaTipoB = licenciasTitular.stream()
            .anyMatch(licencia -> licencia.getClase().equalsIgnoreCase("B") &&
                    licencia.getFechaInicio().before(java.sql.Date.valueOf(java.time.LocalDate.now().minusYears(1))));

        // Validar que tenga una licencia tipo B con más de un año de antigüedad
        if ((claseLicencia.equalsIgnoreCase("C") || claseLicencia.equalsIgnoreCase("D") || claseLicencia.equalsIgnoreCase("E")) && !tieneLicenciaTipoB) {
            throw new ClaseEmisionInvalidaException("El titular debe haber tenido una licencia tipo B con al menos un año de antigüedad para obtener una licencia de tipo C, D o E.");
        }

        // Validar que el titular tenga menos de 65 años
          if (titular.getEdad() >= 65 && (claseLicencia.equalsIgnoreCase("C") || claseLicencia.equalsIgnoreCase("D") || claseLicencia.equalsIgnoreCase("E"))) {
            throw new ClaseEmisionInvalidaException("El titular debe tener menos de 65 años para obtener una licencia de tipo C, D o E.");
        }
            // Emitir la licencia
            Licencia nuevaLicencia = new Licencia();
            nuevaLicencia.setTitular(titular);
            nuevaLicencia.setClase(claseLicencia);
            nuevaLicencia.setEstaVigente(true);
            nuevaLicencia.setFechaInicio(java.sql.Date.valueOf(java.time.LocalDate.now()));

            return nuevaLicencia;
        }
    */

}
