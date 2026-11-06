package com.tecsup.ticketservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Respuesta de GET /api/tickets/{id}/detalle:
 * el ticket + los datos del usuario y del tecnico consultados a sus microservicios.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketDetalleResponseDTO {

    private TicketResponseDTO ticket;
    private UsuarioDTO usuario;
    private TecnicoDTO tecnico;
}
