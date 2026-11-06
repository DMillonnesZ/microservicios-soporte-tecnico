package com.tecsup.ticketservice.services;

import com.tecsup.ticketservice.client.TecnicoClient;
import com.tecsup.ticketservice.client.UsuarioClient;
import com.tecsup.ticketservice.dto.*;
import com.tecsup.ticketservice.exception.BusinessException;
import com.tecsup.ticketservice.exception.ResourceNotFoundException;
import com.tecsup.ticketservice.mapper.TicketMapper;
import com.tecsup.ticketservice.model.Estado;
import com.tecsup.ticketservice.model.Ticket;
import com.tecsup.ticketservice.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;
    private final UsuarioClient usuarioClient;
    private final TecnicoClient tecnicoClient;

    @Value("${app.ticket.requiere-usuario-verificado:true}")
    private boolean requiereUsuarioVerificado;

    @Override
    @Transactional(readOnly = true)
    public List<TicketResponseDTO> listar() {
        return ticketMapper.toResponseList(ticketRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public TicketResponseDTO buscarPorId(Long id) {
        return ticketMapper.toResponse(obtenerTicket(id));
    }

    @Override
    @Transactional(readOnly = true)
    public TicketDetalleResponseDTO buscarDetalle(Long id) {
        Ticket ticket = obtenerTicket(id);
        UsuarioDTO usuario = usuarioClient.obtenerUsuario(ticket.getIdUsuario());
        TecnicoDTO tecnico = tecnicoClient.obtenerTecnico(ticket.getIdTecnico());
        return new TicketDetalleResponseDTO(ticketMapper.toResponse(ticket), usuario, tecnico);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TicketResponseDTO> listarPorUsuario(Long idUsuario) {
        return ticketMapper.toResponseList(ticketRepository.findByIdUsuario(idUsuario));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TicketResponseDTO> listarPorTecnico(Long idTecnico) {
        return ticketMapper.toResponseList(ticketRepository.findByIdTecnico(idTecnico));
    }

    /** Crea el ticket en estado ABIERTO, validando antes que usuario y tecnico existan. */
    @Override
    @Transactional
    public TicketResponseDTO registrar(TicketRequestDTO dto) {
        validarReferencias(dto.getIdUsuario(), dto.getIdTecnico());
        Ticket ticket = ticketMapper.toEntity(dto);
        ticket.setEstado(Estado.ABIERTO);
        return ticketMapper.toResponse(ticketRepository.save(ticket));
    }

    /** Edita los datos del ticket (no el estado). Un ticket CERRADO no se puede editar. */
    @Override
    @Transactional
    public TicketResponseDTO actualizar(Long id, TicketRequestDTO dto) {
        Ticket ticket = obtenerTicket(id);
        if (ticket.getEstado() == Estado.CERRADO) {
            throw new BusinessException(HttpStatus.CONFLICT, "El ticket " + id + " esta CERRADO y no se puede editar");
        }
        validarReferencias(dto.getIdUsuario(), dto.getIdTecnico());
        ticketMapper.updateEntity(dto, ticket);
        return ticketMapper.toResponse(ticketRepository.save(ticket));
    }

    /** Cambia el estado respetando las transiciones definidas en el enum Estado. */
    @Override
    @Transactional
    public TicketResponseDTO cambiarEstado(Long id, Estado nuevoEstado) {
        Ticket ticket = obtenerTicket(id);
        Estado actual = ticket.getEstado();
        if (actual == nuevoEstado) {
            throw new BusinessException(HttpStatus.CONFLICT, "El ticket ya se encuentra en estado " + actual);
        }
        if (!actual.puedeCambiarA(nuevoEstado)) {
            throw new BusinessException(HttpStatus.CONFLICT,
                    "No se puede cambiar el ticket de " + actual + " a " + nuevoEstado);
        }
        ticket.setEstado(nuevoEstado);
        if (nuevoEstado == Estado.CERRADO) {
            ticket.setFechaCierre(LocalDateTime.now());
        }
        return ticketMapper.toResponse(ticketRepository.save(ticket));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Ticket ticket = obtenerTicket(id);
        ticketRepository.delete(ticket);
    }

    // ----------------- metodos de apoyo -----------------

    private Ticket obtenerTicket(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket con id " + id + " no existe"));
    }

    /** Consulta a los otros microservicios por HTTP: lanza 404 / 409 / 503 segun el caso. */
    private void validarReferencias(Long idUsuario, Long idTecnico) {
        UsuarioDTO usuario = usuarioClient.obtenerUsuario(idUsuario);
        if (requiereUsuarioVerificado && !usuario.isVerificado()) {
            throw new BusinessException(HttpStatus.CONFLICT,
                    "El usuario con id " + idUsuario + " no ha verificado su email");
        }
        tecnicoClient.obtenerTecnico(idTecnico);
    }
}
