package com.tecsup.ticketservice.dto;

import com.tecsup.ticketservice.model.Estado;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** JSON para PATCH /api/tickets/{id}/estado  ->  { "estado": "EN_PROCESO" } */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CambioEstadoDTO {

    @NotNull(message = "El estado es obligatorio (ABIERTO, EN_PROCESO o CERRADO)")
    private Estado estado;
}
