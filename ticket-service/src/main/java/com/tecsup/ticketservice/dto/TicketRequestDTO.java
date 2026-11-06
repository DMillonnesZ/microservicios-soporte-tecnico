package com.tecsup.ticketservice.dto;

import com.tecsup.ticketservice.model.Prioridad;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de ENTRADA para crear / editar un ticket.
 * No incluye "estado": al crear siempre es ABIERTO y se cambia con PATCH /estado.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketRequestDTO {

    @NotNull(message = "El idUsuario es obligatorio")
    @Positive(message = "El idUsuario debe ser mayor a 0")
    private Long idUsuario;

    @NotNull(message = "El idTecnico es obligatorio")
    @Positive(message = "El idTecnico debe ser mayor a 0")
    private Long idTecnico;

    @NotBlank(message = "El asunto es obligatorio")
    @Size(min = 5, max = 150, message = "El asunto debe tener entre 5 y 150 caracteres")
    private String asunto;

    @NotBlank(message = "La descripcion es obligatoria")
    @Size(min = 10, max = 1000, message = "La descripcion debe tener entre 10 y 1000 caracteres")
    private String descripcion;

    @NotNull(message = "La prioridad es obligatoria (BAJA, MEDIA o ALTA)")
    private Prioridad prioridad;
}
