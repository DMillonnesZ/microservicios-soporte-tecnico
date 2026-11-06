package com.tecsup.ticketservice.repository;

import com.tecsup.ticketservice.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findByIdUsuario(Long idUsuario);

    List<Ticket> findByIdTecnico(Long idTecnico);
}
