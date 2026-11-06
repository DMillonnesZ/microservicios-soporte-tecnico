package com.tecsup.ticketservice.services;

import com.tecsup.ticketservice.dto.TicketDetalleResponseDTO;
import com.tecsup.ticketservice.dto.TicketRequestDTO;
import com.tecsup.ticketservice.dto.TicketResponseDTO;
import com.tecsup.ticketservice.model.Estado;

import java.util.List;

public interface TicketService {

    List<TicketResponseDTO> listar();

    TicketResponseDTO buscarPorId(Long id);

    TicketDetalleResponseDTO buscarDetalle(Long id);

    List<TicketResponseDTO> listarPorUsuario(Long idUsuario);

    List<TicketResponseDTO> listarPorTecnico(Long idTecnico);

    TicketResponseDTO registrar(TicketRequestDTO dto);

    TicketResponseDTO actualizar(Long id, TicketRequestDTO dto);

    TicketResponseDTO cambiarEstado(Long id, Estado nuevoEstado);

    void eliminar(Long id);
}
