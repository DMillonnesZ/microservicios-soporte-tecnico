package com.tecsup.tecnicoservice.mapper;

import com.tecsup.tecnicoservice.dto.TecnicoRequestDTO;
import com.tecsup.tecnicoservice.dto.TecnicoResponseDTO;
import com.tecsup.tecnicoservice.model.Tecnico;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * MapStruct genera la implementacion (TecnicoMapperImpl) al compilar.
 * Ver: target/generated-sources/annotations/.../mapper/TecnicoMapperImpl.java
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TecnicoMapper {

    // JSON de entrada (RequestDTO) -> Entidad
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fechaRegistro", ignore = true)
    Tecnico toEntity(TecnicoRequestDTO dto);

    // Entidad -> JSON de salida (ResponseDTO)
    TecnicoResponseDTO toResponse(Tecnico tecnico);

    List<TecnicoResponseDTO> toResponseList(List<Tecnico> tecnicos);

    // PUT: copia los datos del DTO sobre la entidad existente
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fechaRegistro", ignore = true)
    void updateEntity(TecnicoRequestDTO dto, @MappingTarget Tecnico tecnico);
}
