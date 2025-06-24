package com.gestionlicencias.gestionlicenciasconducir.controller;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.gestionlicencias.gestionlicenciasconducir.model.Licencia;
import com.gestionlicencias.gestionlicenciasconducir.model.TipoDocumento;
import com.gestionlicencias.gestionlicenciasconducir.model.Titular;
import com.gestionlicencias.gestionlicenciasconducir.model.Tramite;
import com.gestionlicencias.gestionlicenciasconducir.service.LicenciaServiceImpl;
import com.gestionlicencias.gestionlicenciasconducir.service.TitularServiceImpl;
import com.gestionlicencias.gestionlicenciasconducir.service.TramiteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class LicenciaControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TitularServiceImpl titularService;

    @Mock
    private LicenciaServiceImpl licenciaService;

    @Mock
    private TramiteServiceImpl tramiteService;

    @InjectMocks
    private LicenciaController licenciaController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(licenciaController).build();
    }

    @Test
    void emitirCopiaLicencia() throws Exception {
        TipoDocumento tipoDocumento = TipoDocumento.DNI;
        String documento = "12345678";
        String claseLicencia = "B";

        Titular titular = new Titular();
        titular.setTipoDocumento(tipoDocumento);
        titular.setDocumento(documento);

        Licencia licencia = new Licencia();
        licencia.setClase(claseLicencia);
        licencia.setTitular(titular);

        Tramite tramite = new Tramite();
        tramite.setIdTramite(1);

        when(titularService.buscarTitularDocumento(tipoDocumento, documento)).thenReturn(titular);
        when(licenciaService.buscarLicenciaPorTitularyClase(titular, claseLicencia)).thenReturn(licencia);
        when(licenciaService.emitirCopiaLicencia(licencia, titular)).thenReturn(tramite);

        mockMvc.perform(get("/api/licencias/emitirCopia")
                .param("tipoDocumento", tipoDocumento.name())
                .param("documento", documento)
                .param("claseLicencia", claseLicencia)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.idTramite").value(1))
            .andExpect(jsonPath("$.tipoDocumento").value(tipoDocumento.name()))
            .andExpect(jsonPath("$.documento").value(documento))
            .andExpect(jsonPath("$.claseLicencia").value(claseLicencia));
    }

    @Test
    void validarRenovacion_licenciaVigente_sinCambiosEnTitular_retornaBadRequest() throws Exception {
        TipoDocumento tipoDocumento = TipoDocumento.DNI;
        String documento = "12345678";
        String claseLicencia = "B";

        Titular titular = new Titular();
        titular.setTipoDocumento(tipoDocumento);
        titular.setDocumento(documento);
        titular.setModificado(false); // No fue modificado

        Licencia licencia = new Licencia();
        licencia.setClase(claseLicencia);
        licencia.setTitular(titular);
        licencia.setEstaVigente(true); // Aún está vigente

        when(titularService.buscarTitularDocumento(tipoDocumento, documento)).thenReturn(titular);
        when(licenciaService.buscarLicenciaPorTitularyClase(titular, claseLicencia)).thenReturn(licencia);
        when(licenciaService.sePuedeRenovar(licencia, titular)).thenReturn(false);

        mockMvc.perform(get("/api/licencias/validarRenovacion")
                .param("tipoDocumento", tipoDocumento.name())
                .param("documento", documento)
                .param("claseLicencia", claseLicencia))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("La licencia no está vencida ni hubo modificaciones en los datos del titular asociado. No se puede renovar."));
    }

    @Test
    void validarRenovacion_titularModificado_licenciaVigente_retornaOk() throws Exception {
        TipoDocumento tipoDocumento = TipoDocumento.DNI;
        String documento = "12345678";
        String claseLicencia = "B";

        Titular titular = new Titular();
        titular.setTipoDocumento(tipoDocumento);
        titular.setDocumento(documento);
        titular.setModificado(true); // Fue modificado

        Licencia licencia = new Licencia();
        licencia.setClase(claseLicencia);
        licencia.setTitular(titular);
        licencia.setEstaVigente(true); // Sigue vigente, pero se permite renovar

        when(titularService.buscarTitularDocumento(tipoDocumento, documento)).thenReturn(titular);
        when(licenciaService.buscarLicenciaPorTitularyClase(titular, claseLicencia)).thenReturn(licencia);
        when(licenciaService.sePuedeRenovar(licencia, titular)).thenReturn(true);

        mockMvc.perform(get("/api/licencias/validarRenovacion")
                .param("tipoDocumento", tipoDocumento.name())
                .param("documento", documento)
                .param("claseLicencia", claseLicencia))
            .andExpect(status().isOk());
    }

}



