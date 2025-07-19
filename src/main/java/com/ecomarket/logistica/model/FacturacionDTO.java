package com.ecomarket.logistica.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class FacturacionDTO {

    @JsonProperty("idFacturacion")
    private Integer idFacturacion;

    @JsonProperty("tipo_documento")
    private String tipoDocumento;

    @JsonProperty("id_venta")
    private Integer idVenta;

    @JsonProperty("id")
    private Integer idUsuario;

    @JsonProperty("fecha_emision")
    private LocalDate fechaEmision;
}
