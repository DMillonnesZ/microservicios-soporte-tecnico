package com.tecsup.usuarioservice.controller;

import com.tecsup.usuarioservice.dto.UsuarioRequestDTO;
import com.tecsup.usuarioservice.dto.UsuarioResponseDTO;
import com.tecsup.usuarioservice.dto.VerificacionRequestDTO;
import com.tecsup.usuarioservice.services.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    // GET http://localhost:8081/api/usuarios
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listar() {
        return ResponseEntity.ok(usuarioService.listar());
    }

    // GET http://localhost:8081/api/usuarios/{id}
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }

    // POST http://localhost:8081/api/usuarios
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> registrar(@Valid @RequestBody UsuarioRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.registrar(dto));
    }

    // PUT http://localhost:8081/api/usuarios/{id}
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> actualizar(@PathVariable Long id,
                                                         @Valid @RequestBody UsuarioRequestDTO dto) {
        return ResponseEntity.ok(usuarioService.actualizar(id, dto));
    }

    // DELETE http://localhost:8081/api/usuarios/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        usuarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // POST http://localhost:8081/api/usuarios/{id}/verificar   body: { "codigo": "123456" }
    @PostMapping("/{id}/verificar")
    public ResponseEntity<UsuarioResponseDTO> verificar(@PathVariable Long id,
                                                        @Valid @RequestBody VerificacionRequestDTO dto) {
        return ResponseEntity.ok(usuarioService.verificarEmail(id, dto.getCodigo()));
    }

    // POST http://localhost:8081/api/usuarios/{id}/reenviar-codigo
    @PostMapping("/{id}/reenviar-codigo")
    public ResponseEntity<Map<String, String>> reenviarCodigo(@PathVariable Long id) {
        usuarioService.reenviarCodigo(id);
        return ResponseEntity.ok(Map.of("mensaje", "Se envio un nuevo codigo de verificacion"));
    }
}
