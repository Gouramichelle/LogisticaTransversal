package com.ecomarket.logistica.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="logistica")
public class LogisticaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idLogistica;

    @Column(name= "id_usuario")
    @JsonProperty ("id")
    private Integer id;

    @Column(name= "id_factura")
    @JsonProperty ("idFactura")
    private Integer idFactura;

    @Column (name= "estado")
    private String estado;


}
