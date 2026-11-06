package com.tecsup.tecnicoservice.services;

import com.tecsup.tecnicoservice.dto.TecnicoRequestDTO;
import com.tecsup.tecnicoservice.dto.TecnicoResponseDTO;

import java.util.List;

public interface TecnicoService {

    List<TecnicoResponseDTO> listar();

    TecnicoResponseDTO buscarPorId(Long id);

    TecnicoResponseDTO registrar(TecnicoRequestDTO dto);

    TecnicoResponseDTO actualizar(Long id, TecnicoRequestDTO dto);

    void eliminar(Long id);
}
