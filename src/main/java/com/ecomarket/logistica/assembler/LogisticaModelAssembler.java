package com.ecomarket.logistica.assembler;
import com.ecomarket.logistica.controller.LogisticaController;
import com.ecomarket.logistica.model.LogisticaEntity;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class LogisticaModelAssembler implements RepresentationModelAssembler<LogisticaEntity, EntityModel<LogisticaEntity>> {

    @Override
    public EntityModel<LogisticaEntity> toModel(LogisticaEntity logistica) {
        return EntityModel.of(logistica,
                linkTo(methodOn(LogisticaController.class).getLogisticaById(logistica.getId())).withSelfRel(),
                linkTo(methodOn(LogisticaController.class).getAllLogisticas()).withRel("logisticas"));
    }
}