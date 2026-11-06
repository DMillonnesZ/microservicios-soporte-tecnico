package com.tecsup.usuarioservice.mapper;

import com.tecsup.usuarioservice.dto.UsuarioRequestDTO;
import com.tecsup.usuarioservice.dto.UsuarioResponseDTO;
import com.tecsup.usuarioservice.model.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * MapStruct genera la implementacion (UsuarioMapperImpl) al compilar.
 * Ver: target/generated-sources/annotations/.../mapper/UsuarioMapperImpl.java
 * componentModel = SPRING -> se registra como @Component y se inyecta en el Service.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UsuarioMapper {

    // JSON de entrada (RequestDTO) -> Entidad
    // Los campos que controla el sistema se ignoran (no vienen del cliente)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "verificado", ignore = true)
    @Mapping(target = "codigoVerificacion", ignore = true)
    @Mapping(target = "codigoExpiracion", ignore = true)
    @Mapping(target = "fechaRegistro", ignore = true)
    Usuario toEntity(UsuarioRequestDTO dto);

    // Entidad -> JSON de salida (ResponseDTO). El codigo NUNCA se devuelve.
    UsuarioResponseDTO toResponse(Usuario usuario);

    List<UsuarioResponseDTO> toResponseList(List<Usuario> usuarios);

    // PUT: copia los datos del DTO sobre la entidad existente (no crea una nueva)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "verificado", ignore = true)
    @Mapping(target = "codigoVerificacion", ignore = true)
    @Mapping(target = "codigoExpiracion", ignore = true)
    @Mapping(target = "fechaRegistro", ignore = true)
    void updateEntity(UsuarioRequestDTO dto, @MappingTarget Usuario usuario);
}
