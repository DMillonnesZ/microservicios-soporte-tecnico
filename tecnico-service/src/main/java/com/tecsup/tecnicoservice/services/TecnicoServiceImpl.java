package com.tecsup.tecnicoservice.services;

import com.tecsup.tecnicoservice.dto.TecnicoRequestDTO;
import com.tecsup.tecnicoservice.dto.TecnicoResponseDTO;
import com.tecsup.tecnicoservice.exception.DuplicateResourceException;
import com.tecsup.tecnicoservice.exception.ResourceNotFoundException;
import com.tecsup.tecnicoservice.mapper.TecnicoMapper;
import com.tecsup.tecnicoservice.model.Tecnico;
import com.tecsup.tecnicoservice.repository.TecnicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TecnicoServiceImpl implements TecnicoService {

    private final TecnicoRepository tecnicoRepository;
    private final TecnicoMapper tecnicoMapper;

    @Override
    @Transactional(readOnly = true)
    public List<TecnicoResponseDTO> listar() {
        return tecnicoMapper.toResponseList(tecnicoRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public TecnicoResponseDTO buscarPorId(Long id) {
        return tecnicoMapper.toResponse(obtenerTecnico(id));
    }

    @Override
    @Transactional
    public TecnicoResponseDTO registrar(TecnicoRequestDTO dto) {
        if (tecnicoRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateResourceException("Ya existe un tecnico con el email " + dto.getEmail());
        }
        Tecnico tecnico = tecnicoMapper.toEntity(dto);
        return tecnicoMapper.toResponse(tecnicoRepository.save(tecnico));
    }

    @Override
    @Transactional
    public TecnicoResponseDTO actualizar(Long id, TecnicoRequestDTO dto) {
        Tecnico tecnico = obtenerTecnico(id);
        if (tecnicoRepository.existsByEmailAndIdNot(dto.getEmail(), id)) {
            throw new DuplicateResourceException("Ya existe otro tecnico con el email " + dto.getEmail());
        }
        tecnicoMapper.updateEntity(dto, tecnico);
        return tecnicoMapper.toResponse(tecnicoRepository.save(tecnico));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Tecnico tecnico = obtenerTecnico(id);
        tecnicoRepository.delete(tecnico);
    }

    private Tecnico obtenerTecnico(Long id) {
        return tecnicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tecnico con id " + id + " no existe"));
    }
}
