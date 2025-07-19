package com.ecomarket.logistica.controller;

import com.ecomarket.logistica.assembler.LogisticaModelAssembler;
import com.ecomarket.logistica.model.LogisticaEntity;
import com.ecomarket.logistica.service.LogisticaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/logistica")
@Tag(name = "Logística", description = "APIs del microservicio de Logística")
public class LogisticaController {

    @Autowired
    private LogisticaService logisticaService;

    @Autowired
    private LogisticaModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener todas las órdenes logísticas", description = "Devuelve todas las órdenes de logística registradas")
    public CollectionModel<EntityModel<LogisticaEntity>> getAllLogisticas() {
        List<EntityModel<LogisticaEntity>> logisticas = logisticaService.obtenerTodasLogisticas()
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(logisticas,
                linkTo(methodOn(LogisticaController.class).getAllLogisticas()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener logística por ID", description = "Busca y devuelve una orden logística por su ID")
    public EntityModel<LogisticaEntity> getLogisticaById(@PathVariable Integer id) {
        LogisticaEntity logistica = logisticaService.obtenerLogisticaPorId(id);
        return assembler.toModel(logistica);
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crear nueva logística", description = "Registra una nueva orden de logística")
    public ResponseEntity<EntityModel<LogisticaEntity>> createLogistica(@RequestBody LogisticaEntity logistica) {
        LogisticaEntity nueva = logisticaService.crearLogistica(logistica);
        return ResponseEntity
                .created(linkTo(methodOn(LogisticaController.class).getLogisticaById(nueva.getId())).toUri())
                .body(assembler.toModel(nueva));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar logística", description = "Actualiza una orden logística existente")
    public ResponseEntity<EntityModel<LogisticaEntity>> updateLogistica(
            @PathVariable Integer id, @RequestBody LogisticaEntity logistica) {
        LogisticaEntity actualizada = logisticaService.actualizarLogistica(id, logistica);
        return ResponseEntity.ok(assembler.toModel(actualizada));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Eliminar logística", description = "Elimina una orden logística por su ID")
    public ResponseEntity<?> deleteLogistica(@PathVariable Integer id) {
        logisticaService.eliminarLogistica(id);
        return ResponseEntity.noContent().build();
    }
}
