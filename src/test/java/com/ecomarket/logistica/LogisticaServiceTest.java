package com.ecomarket.logistica;
import com.ecomarket.logistica.exception.ResourceNotFoundException;
import com.ecomarket.logistica.model.FacturacionDTO;
import com.ecomarket.logistica.model.LogisticaEntity;
import com.ecomarket.logistica.model.UsuarioDTO;
import com.ecomarket.logistica.repository.LogisticaRepository;
import com.ecomarket.logistica.service.FacturacionService;
import com.ecomarket.logistica.service.LogisticaService;
import com.ecomarket.logistica.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@SpringBootTest
public class LogisticaServiceTest {

    @Autowired
    private LogisticaService logisticaService;

    @MockitoBean
    private LogisticaRepository logisticaRepository;

    @MockitoBean
    private UsuarioService usuarioService;

    @MockitoBean
    private FacturacionService facturacionService;

    private LogisticaEntity logistica;

    @BeforeEach
    void setUp() {
        logistica = new LogisticaEntity();
        logistica.setId(1);
        logistica.setEstado("Pendiente");
    }

    @Test
    void obtenerTodasLogisticas_ok() {
        when(logisticaRepository.findAll()).thenReturn(List.of(logistica));

        List<LogisticaEntity> resultado = logisticaService.obtenerTodasLogisticas();

        assertThat(resultado).hasSize(1);
        assertEquals("Pendiente", resultado.get(0).getEstado());
    }

    @Test
    void obtenerLogisticaPorId_existente() {
        when(logisticaRepository.findById(1)).thenReturn(Optional.of(logistica));

        LogisticaEntity resultado = logisticaService.obtenerLogisticaPorId(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
    }

    @Test
    void obtenerLogisticaPorId_noExiste() {
        when(logisticaRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> logisticaService.obtenerLogisticaPorId(999));
    }

    @Test
    void crearLogistica_ok() {
        when(usuarioService.obtenerUsuarioId(1)).thenReturn(new UsuarioDTO());
        when(facturacionService.obtenerFacturacionPorId(1)).thenReturn(new FacturacionDTO());
        when(logisticaRepository.save(any())).thenReturn(logistica);

        LogisticaEntity resultado = logisticaService.crearLogistica(logistica);

        assertNotNull(resultado);
        assertEquals("Pendiente", resultado.getEstado());
        verify(logisticaRepository).save(logistica);
    }

    @Test
    void crearLogistica_usuarioNoExiste() {
        when(usuarioService.obtenerUsuarioId(1)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class,
                () -> logisticaService.crearLogistica(logistica));
    }

    @Test
    void crearLogistica_facturacionNoExiste() {
        when(usuarioService.obtenerUsuarioId(1)).thenReturn(new UsuarioDTO());
        when(facturacionService.obtenerFacturacionPorId(1)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class,
                () -> logisticaService.crearLogistica(logistica));
    }

    @Test
    void actualizarLogistica_ok() {
        LogisticaEntity nueva = new LogisticaEntity();
        nueva.setId(1);
        nueva.setEstado("Enviado");

        when(logisticaRepository.findById(1)).thenReturn(Optional.of(logistica));
        when(usuarioService.obtenerUsuarioId(1)).thenReturn(new UsuarioDTO());
        when(facturacionService.obtenerFacturacionPorId(1)).thenReturn(new FacturacionDTO());
        when(logisticaRepository.save(any())).thenReturn(logistica);

        LogisticaEntity resultado = logisticaService.actualizarLogistica(1, nueva);

        assertNotNull(resultado);
        verify(logisticaRepository).save(any());
    }

    @Test
    void actualizarLogistica_noExiste() {
        when(logisticaRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> logisticaService.actualizarLogistica(999, logistica));
    }

    @Test
    void actualizarLogistica_usuarioNoExiste() {
        when(logisticaRepository.findById(1)).thenReturn(Optional.of(logistica));
        when(usuarioService.obtenerUsuarioId(1)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class,
                () -> logisticaService.actualizarLogistica(1, logistica));
    }

    @Test
    void actualizarLogistica_facturacionNoExiste() {
        when(logisticaRepository.findById(1)).thenReturn(Optional.of(logistica));
        when(usuarioService.obtenerUsuarioId(1)).thenReturn(new UsuarioDTO());
        when(facturacionService.obtenerFacturacionPorId(1)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class,
                () -> logisticaService.actualizarLogistica(1, logistica));
    }

    @Test
    void eliminarLogistica_ok() {
        when(logisticaRepository.findById(1)).thenReturn(Optional.of(logistica));
        doNothing().when(logisticaRepository).deleteById(1);

        logisticaService.eliminarLogistica(1);

        verify(logisticaRepository).deleteById(1);
    }

    @Test
    void eliminarLogistica_noExiste() {
        when(logisticaRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> logisticaService.eliminarLogistica(999));
    }
}
