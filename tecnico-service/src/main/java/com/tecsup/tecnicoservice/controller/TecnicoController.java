package com.tecsup.tecnicoservice.controller;

import com.tecsup.tecnicoservice.dto.TecnicoRequestDTO;
import com.tecsup.tecnicoservice.dto.TecnicoResponseDTO;
import com.tecsup.tecnicoservice.services.TecnicoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tecnicos")
@RequiredArgsConstructor
public class TecnicoController {

    private final TecnicoService tecnicoService;

    // GET http://localhost:8082/api/tecnicos
    @GetMapping
    public ResponseEntity<List<TecnicoResponseDTO>> listar() {
        return ResponseEntity.ok(tecnicoService.listar());
    }

    // GET http://localhost:8082/api/tecnicos/{id}
    @GetMapping("/{id}")
    public ResponseEntity<TecnicoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(tecnicoService.buscarPorId(id));
    }

    // POST http://localhost:8082/api/tecnicos
    @PostMapping
    public ResponseEntity<TecnicoResponseDTO> registrar(@Valid @RequestBody TecnicoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tecnicoService.registrar(dto));
    }

    // PUT http://localhost:8082/api/tecnicos/{id}
    @PutMapping("/{id}")
    public ResponseEntity<TecnicoResponseDTO> actualizar(@PathVariable Long id,
                                                         @Valid @RequestBody TecnicoRequestDTO dto) {
        return ResponseEntity.ok(tecnicoService.actualizar(id, dto));
    }

    // DELETE http://localhost:8082/api/tecnicos/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        tecnicoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
