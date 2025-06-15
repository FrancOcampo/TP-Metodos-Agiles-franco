package com.gestionlicencias.gestionlicenciasconducir.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gestionlicencias.gestionlicenciasconducir.Exception.ClaseEmisionInvalidaException;
import com.gestionlicencias.gestionlicenciasconducir.dto.LicenciaRecord;
import com.gestionlicencias.gestionlicenciasconducir.dto.TitularRecord;
import com.gestionlicencias.gestionlicenciasconducir.dto.LicenciaListadoRecord;
import com.gestionlicencias.gestionlicenciasconducir.model.Licencia;
import com.gestionlicencias.gestionlicenciasconducir.model.Titular;
import com.gestionlicencias.gestionlicenciasconducir.model.Tramite;
import com.gestionlicencias.gestionlicenciasconducir.repository.LicenciaRepository;

@Service
public class LicenciaServiceImpl implements LicenciaService {

    private static final List<String> CLASES_VALIDAS = Arrays.asList("A", "B", "C", "E", "G");
    private static final List<Integer> VIGENCIAS_VALIDAS = Arrays.asList(1, 3, 4, 5);

    private final LicenciaRepository repository;
    private final TramiteService tramiteService;
    private final UsuarioService usuarioService;

    @Autowired
    public LicenciaServiceImpl(LicenciaRepository repository, TramiteService tramiteService, UsuarioService usuarioService) {
        this.repository = repository;
        this.tramiteService = tramiteService;
        this.usuarioService = usuarioService;
    }

    @Override
    public Float calcularCostoLicencia(String clase, Integer vigencia) {
        // Validar valores nulos
        if (clase == null) {
            throw new IllegalArgumentException("Clase de licencia no válida. Las clases válidas son: A, B, C, E, G");
        }
        if (vigencia == null) {
            throw new IllegalArgumentException("Vigencia no válida. Las vigencias válidas son: 1, 3, 4, 5 años");
        }

        // Validar clase
        if (!CLASES_VALIDAS.contains(clase.toUpperCase())) {
            throw new IllegalArgumentException("Clase de licencia no válida. Las clases válidas son: A, B, C, E, G");
        }

        // Validar vigencia
        if (!VIGENCIAS_VALIDAS.contains(vigencia)) {
            throw new IllegalArgumentException("Vigencia no válida. Las vigencias válidas son: 1, 3, 4, 5 años");
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

    public Licencia emitirLicencia(Titular titular, String claseLicencia, String observaciones) throws ClaseEmisionInvalidaException {

        // Obtener las licencias del titular
        List<Licencia> licenciasTitular = titular.getLicencias();

        // Buscar si el titular tiene una licencia vigente del tipo solicitado 
        Optional<Licencia> licenciaTipo = licenciasTitular.stream()
            .filter(licencia -> licencia.getClase().equalsIgnoreCase(claseLicencia))
            .findFirst();

        if (licenciaTipo.isPresent()) {
            Licencia L = licenciaTipo.get();
            if (!L.getEstaVigente()) {
                throw new ClaseEmisionInvalidaException("El titular ya posee una licencia de tipo " + claseLicencia + " que no está vigente. Debe renovarla.");
            } else if (L.getFechaVencimiento().before(java.sql.Date.valueOf(java.time.LocalDate.now()))) {
                L.setEstaVigente(false);
                repository.save(L);
                throw new ClaseEmisionInvalidaException("El titular ya posee una licencia de tipo " + claseLicencia + " que está vencida. Debe renovarla.");
            } else throw new ClaseEmisionInvalidaException("El titular ya posee una licencia vigente de tipo " + claseLicencia + ".");
        }

        else if(claseLicencia.equalsIgnoreCase("C") || claseLicencia.equalsIgnoreCase("D") || claseLicencia.equalsIgnoreCase("E")) {

        // Validar que el titular tenga más de 21 años
        if (titular.getEdad() < 21 && (claseLicencia.equalsIgnoreCase("C") || claseLicencia.equalsIgnoreCase("D") || claseLicencia.equalsIgnoreCase("E"))) {
            throw new ClaseEmisionInvalidaException("El titular debe tener al menos 21 años para obtener una licencia de tipo C, D o E.");
        }

        // Buscar si el titular tiene una licencia de tipo B
        boolean tieneLicenciaTipoB = licenciasTitular.stream()
            .anyMatch(licencia -> licencia.getClase().equalsIgnoreCase("B") &&
                    licencia.getFechaInicio().before(java.sql.Date.valueOf(java.time.LocalDate.now().minusYears(1))));

        // Validar que tenga una licencia tipo B con más de un año de antigüedad
        if (!tieneLicenciaTipoB) {
            throw new ClaseEmisionInvalidaException("El titular debe haber tenido una licencia tipo B con al menos un año de antigüedad para obtener una licencia de tipo C, D o E.");
        }

        // Validar que el titular tenga menos de 65 años
        if (titular.getEdad() >= 65 && (claseLicencia.equalsIgnoreCase("C") || claseLicencia.equalsIgnoreCase("D") || claseLicencia.equalsIgnoreCase("E"))) {
            throw new ClaseEmisionInvalidaException("El titular debe tener menos de 65 años para obtener una licencia de tipo C, D o E.");
        }

        } else if (titular.getEdad() < 17) {
            throw new ClaseEmisionInvalidaException("El titular debe tener al menos 17 años para obtener una licencia de tipo A, B, F o G.");
        }
        
        // Emitir la licencia
        Licencia nuevaLicencia = new Licencia();
        nuevaLicencia.setTitular(titular);
        nuevaLicencia.setClase(claseLicencia);
        nuevaLicencia.setEstaVigente(true);
        nuevaLicencia.setObservaciones(observaciones);
        nuevaLicencia.setFechaInicio(java.sql.Date.valueOf(java.time.LocalDate.now()));

        // Calcular años de vigencia según titular y clase de licencia
        int aniosVigencia = calcularVigenciaLicencia(titular, claseLicencia);

        int anioVencimiento = LocalDate.now().getYear() + aniosVigencia;

        // Convertir fecha de nacimiento (Date) a LocalDate
        LocalDate fechaNacimiento = ((java.sql.Date) titular.getFechaNacimiento()).toLocalDate();

        // Crear la fecha de vencimiento con mismo mes y día que el nacimiento, pero en el año calculado
        LocalDate fechaVencimiento = LocalDate.of(
                anioVencimiento,
                fechaNacimiento.getMonth(),
                fechaNacimiento.getDayOfMonth()
        );

        // Establecer la fecha de vencimiento en la licencia (conversión a java.sql.Date)
        nuevaLicencia.setFechaVencimiento(java.sql.Date.valueOf(fechaVencimiento));

        repository.save(nuevaLicencia);

        //Registro del trámite
        Tramite tramite = new Tramite();
        tramite.setFecha(java.sql.Date.valueOf(java.time.LocalDate.now()));
        tramite.setDescripcion("Emisión de licencia de conducir clase " + claseLicencia);
        tramite.setCosto(calcularCostoLicencia(claseLicencia, aniosVigencia));
        tramite.setTitularAsociado(titular);
        tramite.setUsuarioResponsable(usuarioService.buscarUsuarioPorId(1)); // POR AHORA HASTA QUE HAGAMOS EL OTRO SPRINT
        tramite.setLicenciaAsociada(nuevaLicencia);
        tramiteService.registrarTramite(tramite);

        return nuevaLicencia;

    }

    public int calcularVigenciaLicencia(Titular titular, String claseLicencia) {

        LocalDate fechaNacimientoTitular = titular.convertirDateALocalDate(titular.getFechaNacimiento());
        int edadTitular = Period.between(fechaNacimientoTitular, LocalDate.now()).getYears();

        int aniosVigencia = 0;

        if(edadTitular < 21) {
            boolean renovacion = false;
            
            for(Licencia L : titular.getLicencias()) {
                if(L.getClase().equalsIgnoreCase(claseLicencia)) {
                    renovacion = true;
                    break;
                }
            }

            if(renovacion) aniosVigencia = 3; 
            else aniosVigencia = 1;

        }

        else if(edadTitular > 21 && edadTitular <= 46) aniosVigencia = 5;

        else if(edadTitular > 46 && edadTitular <= 60) aniosVigencia = 4;

        else if(edadTitular > 60 && edadTitular <= 70) aniosVigencia = 3;

        else if(edadTitular > 70) aniosVigencia = 1;

        return aniosVigencia;
    }

    public Licencia buscarLicenciaPorId(Integer id) {
        return repository.findByIdLicencia(id)
                .orElseThrow(() -> new RuntimeException("Licencia no encontrada."));
    }

    public Licencia obtenerUltimaLicenciaTitular(Titular titular) {
        return repository.findFirstByTitularOrderByFechaInicioDesc(titular);
    }

    @Override
    public List<LicenciaRecord> buscarLicenciasVigentes(String nombreApellido, String grupoSanguineo, String factorRH, boolean donanteOrganos) {
        LocalDate hoy = LocalDate.now();
        List<Licencia> vigentes = repository.findByFechaVencimientoAfter(hoy);

        return vigentes.stream()
            .filter(licencia -> {
                Titular t = licencia.getTitular();
                boolean coincideNombre = (nombreApellido == null || nombreApellido.isBlank()) ||
                    t.getNombre().toLowerCase().contains(nombreApellido.toLowerCase()) ||
                    t.getApellido().toLowerCase().contains(nombreApellido.toLowerCase());
                boolean coincideGrupo = (grupoSanguineo == null || grupoSanguineo.isBlank()) ||
                    grupoSanguineo.equalsIgnoreCase(t.getGrupoSanguineo());
                boolean coincideRH = (factorRH == null || factorRH.isBlank()) ||
                    factorRH.equalsIgnoreCase(t.getFactorRH());
                boolean coincideDonante = !donanteOrganos || t.getDonanteOrganos();
                return coincideNombre && coincideGrupo && coincideRH && coincideDonante;
            })
            .map(licencia -> {
                Titular t = licencia.getTitular();
                TitularRecord titularRecord = new TitularRecord(
                    t.getTipoDocumento(),
                    t.getDocumento(),
                    t.getNombre(),
                    t.getApellido(),
                    t.getFechaNacimiento(),
                    t.getDireccion(),
                    t.getGrupoSanguineo(),
                    t.getFactorRH(),
                    t.getDonanteOrganos()
                );
                return new LicenciaRecord(
                    licencia.getClase(),
                    licencia.getObservaciones(),
                    titularRecord,
                    new java.sql.Date(licencia.getFechaVencimiento().getTime())
                );
            })
            .toList();
    }

    // Metodo auxiliar para filtrar licencias por fechas de forma provisoria
    private List<Licencia> buscarLicenciasPorFechas(Date fechaDesde, Date fechaHasta, List<Licencia> todas) {
        List<Licencia> filtradas = new ArrayList<>();
        for (Licencia l : todas) {
            Date fechaVenc = l.getFechaVencimiento();
            boolean desdeOk = (fechaDesde == null) || !fechaVenc.before(fechaDesde);
            boolean hastaOk = (fechaHasta == null) || !fechaVenc.after(fechaHasta);
            if (desdeOk && hastaOk) {
                filtradas.add(l);
            }
        }
        return filtradas;
    }

    @Override
    public List<LicenciaListadoRecord> buscarLicenciasNoVigentes(Date fechaDesde, Date fechaHasta, String clase) {
        Date hoy = java.sql.Date.valueOf(java.time.LocalDate.now());

        List<LicenciaListadoRecord> licenciasRecord = new ArrayList<>();
        
        List<Licencia> licencias = repository.findLicenciasNoVigentes(hoy, clase);
        /*
         * SOLUCIÓN PROVISORIA: Tuve inconvenientes con la consulta de JPA
         * que no filtraba correctamente por fecha de vencimiento.
         * Habría que revisar la consulta para que funcione correctamente.
         * Por ahora, filtramos por fechas en memoria (esto es muy inficiente
         * si hay muchas licencias, pero es una solución provisoria).
        */
        List<Licencia> filtradas = buscarLicenciasPorFechas(fechaDesde, fechaHasta, licencias);

        for(Licencia l : filtradas) {
            LicenciaListadoRecord lr = new LicenciaListadoRecord(
                l.getTitular().getNombre() + " " + l.getTitular().getApellido(), 
                l.getIdLicencia(), 
                l.getClase(), 
                "No vigente",
                l.getFechaVencimiento()
            );
            licenciasRecord.add(lr);
        }
        
        return licenciasRecord;
    }

}
