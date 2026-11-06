package com.tecsup.ticketservice.controller;

import com.tecsup.ticketservice.dto.CambioEstadoDTO;
import com.tecsup.ticketservice.dto.TicketDetalleResponseDTO;
import com.tecsup.ticketservice.dto.TicketRequestDTO;
import com.tecsup.ticketservice.dto.TicketResponseDTO;
import com.tecsup.ticketservice.services.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    // GET http://localhost:8083/api/tickets
    @GetMapping
    public ResponseEntity<List<TicketResponseDTO>> listar() {
        return ResponseEntity.ok(ticketService.listar());
    }

    // GET http://localhost:8083/api/tickets/{id}
    @GetMapping("/{id}")
    public ResponseEntity<TicketResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(ticketService.buscarPorId(id));
    }

    // GET http://localhost:8083/api/tickets/{id}/detalle  (ticket + usuario + tecnico)
    @GetMapping("/{id}/detalle")
    public ResponseEntity<TicketDetalleResponseDTO> buscarDetalle(@PathVariable Long id) {
        return ResponseEntity.ok(ticketService.buscarDetalle(id));
    }

    // GET http://localhost:8083/api/tickets/usuario/{idUsuario}
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<TicketResponseDTO>> listarPorUsuario(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(ticketService.listarPorUsuario(idUsuario));
    }

    // GET http://localhost:8083/api/tickets/tecnico/{idTecnico}
    @GetMapping("/tecnico/{idTecnico}")
    public ResponseEntity<List<TicketResponseDTO>> listarPorTecnico(@PathVariable Long idTecnico) {
        return ResponseEntity.ok(ticketService.listarPorTecnico(idTecnico));
    }

    // POST http://localhost:8083/api/tickets
    @PostMapping
    public ResponseEntity<TicketResponseDTO> registrar(@Valid @RequestBody TicketRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketService.registrar(dto));
    }

    // PUT http://localhost:8083/api/tickets/{id}
    @PutMapping("/{id}")
    public ResponseEntity<TicketResponseDTO> actualizar(@PathVariable Long id,
                                                        @Valid @RequestBody TicketRequestDTO dto) {
        return ResponseEntity.ok(ticketService.actualizar(id, dto));
    }

    // PATCH http://localhost:8083/api/tickets/{id}/estado   body: { "estado": "EN_PROCESO" }
    @PatchMapping("/{id}/estado")
    public ResponseEntity<TicketResponseDTO> cambiarEstado(@PathVariable Long id,
                                                           @Valid @RequestBody CambioEstadoDTO dto) {
        return ResponseEntity.ok(ticketService.cambiarEstado(id, dto.getEstado()));
    }

    // DELETE http://localhost:8083/api/tickets/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        ticketService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
