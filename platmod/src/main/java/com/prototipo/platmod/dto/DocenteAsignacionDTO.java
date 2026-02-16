package com.prototipo.platmod.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocenteAsignacionDTO {
    private Long idUsuario;
    private String nombre;
    private String especialidad;
    private Boolean asignado;
}
