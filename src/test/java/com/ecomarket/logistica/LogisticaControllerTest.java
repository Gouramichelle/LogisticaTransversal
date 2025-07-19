package com.ecomarket.logistica;
import com.ecomarket.logistica.assembler.LogisticaModelAssembler;
import com.ecomarket.logistica.controller.LogisticaController;
import com.ecomarket.logistica.model.LogisticaEntity;
import com.ecomarket.logistica.service.LogisticaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LogisticaController.class)
public class LogisticaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LogisticaService logisticaService;

    @MockBean
    private LogisticaModelAssembler assembler;

    private LogisticaEntity logistica;

    @BeforeEach
    void setUp() {
        logistica = new LogisticaEntity();
        logistica.setId(1);
        logistica.setEstado("Pendiente");
    }

    @Test
    void getAllLogisticas_ok() throws Exception {
        when(logisticaService.obtenerTodasLogisticas()).thenReturn(List.of(logistica));
        when(assembler.toModel(logistica)).thenReturn(EntityModel.of(logistica));

        mockMvc.perform(get("/logistica").accept(MediaTypes.HAL_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$._embedded.logisticaEntityList[0].id").value(1))
                .andExpect(jsonPath("$._embedded.logisticaEntityList[0].estado").value("Pendiente"));
    }

    @Test
    void getLogisticaById_ok() throws Exception {
        when(logisticaService.obtenerLogisticaPorId(1)).thenReturn(logistica);
        when(assembler.toModel(logistica)).thenReturn(EntityModel.of(logistica));

        mockMvc.perform(get("/logistica/1").accept(MediaTypes.HAL_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.estado").value("Pendiente"));
    }

    @Test
    void createLogistica_ok() throws Exception {
        when(logisticaService.crearLogistica(any(LogisticaEntity.class))).thenReturn(logistica);
        when(assembler.toModel(logistica)).thenReturn(EntityModel.of(logistica));

        mockMvc.perform(post("/logistica")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"estado\":\"Pendiente\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", containsString("/logistica/1")))
                .andExpect(jsonPath("$.estado").value("Pendiente"));
    }

    @Test
    void updateLogistica_ok() throws Exception {
        when(logisticaService.actualizarLogistica(any(), any())).thenReturn(logistica);
        when(assembler.toModel(logistica)).thenReturn(EntityModel.of(logistica));

        mockMvc.perform(put("/logistica/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"estado\":\"Pendiente\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("Pendiente"));
    }

    @Test
    void deleteLogistica_ok() throws Exception {
        mockMvc.perform(delete("/logistica/1"))
                .andExpect(status().isNoContent());
    }
}
