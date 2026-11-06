package com.tecsup.ticketservice.mapper;

import com.tecsup.ticketservice.dto.TicketRequestDTO;
import com.tecsup.ticketservice.dto.TicketResponseDTO;
import com.tecsup.ticketservice.model.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * MapStruct genera TicketMapperImpl al compilar.
 * Los enums (Prioridad, Estado) se copian directo porque tienen el mismo tipo.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TicketMapper {

    // JSON de entrada -> Entidad (estado y fechas los pone el sistema)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "estado", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "fechaActualizacion", ignore = true)
    @Mapping(target = "fechaCierre", ignore = true)
    Ticket toEntity(TicketRequestDTO dto);

    // Entidad -> JSON de salida
    TicketResponseDTO toResponse(Ticket ticket);

    List<TicketResponseDTO> toResponseList(List<Ticket> tickets);

    // PUT: actualiza la entidad existente (el estado NO se toca aqui)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "estado", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "fechaActualizacion", ignore = true)
    @Mapping(target = "fechaCierre", ignore = true)
    void updateEntity(TicketRequestDTO dto, @MappingTarget Ticket ticket);
}
