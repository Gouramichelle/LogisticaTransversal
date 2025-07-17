package com.ecomarket.logistica.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class UsuarioDTO {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("nombre_usuario")
    private String nombreCompleto;

    @JsonProperty("run_usuario")
    private String run;

    @JsonProperty("correo_usuario")
    private String correo;

    @JsonProperty("direccion_usuario")
    private String direccion;

    @JsonProperty("telefono_usuario")
    private String telefono;

    @JsonProperty("giro")
    private String giro;

}
