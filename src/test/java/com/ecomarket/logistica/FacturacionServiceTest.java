package com.ecomarket.logistica;

import com.ecomarket.logistica.model.FacturacionDTO;
import com.ecomarket.logistica.service.FacturacionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@SpringBootTest
public class FacturacionServiceTest {

    @Autowired
    private FacturacionService facturacionService;

    @MockitoBean
    private RestTemplate restTemplate;

    private FacturacionDTO facturacionMock;

    @BeforeEach
    void setUp() {
        facturacionMock = new FacturacionDTO();
        facturacionMock.setIdFacturacion(1);
        facturacionMock.setTipoDocumento("Factura");



        facturacionService.facturacionUrl = "http://localhost:8085/facturacion";
    }

    @Test
    void obtenerFacturacionPorId_ok() {
        String urlEsperada = "http://localhost:8085/facturacion/1";

        when(restTemplate.getForObject(urlEsperada, FacturacionDTO.class))
                .thenReturn(facturacionMock);

        FacturacionDTO resultado = facturacionService.obtenerFacturacionPorId(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getIdFacturacion());
        assertEquals("Factura", resultado.getTipoDocumento());
    }

    @Test
    void obtenerFacturacionPorId_noExiste() {
        String urlEsperada = "http://localhost:8085/facturacion/999";

        when(restTemplate.getForObject(urlEsperada, FacturacionDTO.class))
                .thenReturn(null);

        FacturacionDTO resultado = facturacionService.obtenerFacturacionPorId(999);

        assertNull(resultado);
    }
}