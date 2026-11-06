package com.tecsup.usuarioservice.services;

import com.tecsup.usuarioservice.dto.UsuarioRequestDTO;
import com.tecsup.usuarioservice.dto.UsuarioResponseDTO;

import java.util.List;

public interface UsuarioService {

    List<UsuarioResponseDTO> listar();

    UsuarioResponseDTO buscarPorId(Long id);

    UsuarioResponseDTO registrar(UsuarioRequestDTO dto);

    UsuarioResponseDTO actualizar(Long id, UsuarioRequestDTO dto);

    void eliminar(Long id);

    UsuarioResponseDTO verificarEmail(Long id, String codigo);

    void reenviarCodigo(Long id);
}
