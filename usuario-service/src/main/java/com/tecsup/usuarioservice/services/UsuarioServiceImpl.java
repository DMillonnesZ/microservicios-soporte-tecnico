package com.tecsup.usuarioservice.services;

import com.tecsup.usuarioservice.dto.UsuarioRequestDTO;
import com.tecsup.usuarioservice.dto.UsuarioResponseDTO;
import com.tecsup.usuarioservice.exception.BusinessException;
import com.tecsup.usuarioservice.exception.DuplicateResourceException;
import com.tecsup.usuarioservice.exception.ResourceNotFoundException;
import com.tecsup.usuarioservice.mapper.UsuarioMapper;
import com.tecsup.usuarioservice.model.Usuario;
import com.tecsup.usuarioservice.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private static final SecureRandom RANDOM = new SecureRandom();

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final EmailService emailService;

    @Value("${app.verificacion.minutos-expiracion:10}")
    private int minutosExpiracion;

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> listar() {
        return usuarioMapper.toResponseList(usuarioRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioResponseDTO buscarPorId(Long id) {
        return usuarioMapper.toResponse(obtenerUsuario(id));
    }

    /**
     * Registra el usuario como NO verificado y le envia un codigo por correo.
     * Si el correo falla, la transaccion se revierte (no se guarda el usuario).
     */
    @Override
    @Transactional
    public UsuarioResponseDTO registrar(UsuarioRequestDTO dto) {
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateResourceException("Ya existe un usuario con el email " + dto.getEmail());
        }
        Usuario usuario = usuarioMapper.toEntity(dto);
        asignarNuevoCodigo(usuario);
        Usuario guardado = usuarioRepository.save(usuario);
        emailService.enviarCodigoVerificacion(guardado.getEmail(), guardado.getNombre(), guardado.getCodigoVerificacion());
        return usuarioMapper.toResponse(guardado);
    }

    /**
     * Si el email cambia, el usuario vuelve a estado NO verificado y recibe un nuevo codigo.
     */
    @Override
    @Transactional
    public UsuarioResponseDTO actualizar(Long id, UsuarioRequestDTO dto) {
        Usuario usuario = obtenerUsuario(id);
        if (usuarioRepository.existsByEmailAndIdNot(dto.getEmail(), id)) {
            throw new DuplicateResourceException("Ya existe otro usuario con el email " + dto.getEmail());
        }
        boolean cambioEmail = !usuario.getEmail().equalsIgnoreCase(dto.getEmail());
        usuarioMapper.updateEntity(dto, usuario);
        if (cambioEmail) {
            asignarNuevoCodigo(usuario);
        }
        Usuario guardado = usuarioRepository.save(usuario);
        if (cambioEmail) {
            emailService.enviarCodigoVerificacion(guardado.getEmail(), guardado.getNombre(), guardado.getCodigoVerificacion());
        }
        return usuarioMapper.toResponse(guardado);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Usuario usuario = obtenerUsuario(id);
        usuarioRepository.delete(usuario);
    }

    @Override
    @Transactional
    public UsuarioResponseDTO verificarEmail(Long id, String codigo) {
        Usuario usuario = obtenerUsuario(id);
        if (usuario.isVerificado()) {
            throw new BusinessException(HttpStatus.CONFLICT, "El email ya fue verificado");
        }
        if (usuario.getCodigoExpiracion() == null || LocalDateTime.now().isAfter(usuario.getCodigoExpiracion())) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "El codigo expiro, solicite uno nuevo");
        }
        if (!usuario.getCodigoVerificacion().equals(codigo)) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "El codigo de verificacion es incorrecto");
        }
        usuario.setVerificado(true);
        usuario.setCodigoVerificacion(null);
        usuario.setCodigoExpiracion(null);
        return usuarioMapper.toResponse(usuarioRepository.save(usuario));
    }

    @Override
    @Transactional
    public void reenviarCodigo(Long id) {
        Usuario usuario = obtenerUsuario(id);
        if (usuario.isVerificado()) {
            throw new BusinessException(HttpStatus.CONFLICT, "El email ya fue verificado");
        }
        asignarNuevoCodigo(usuario);
        usuarioRepository.save(usuario);
        emailService.enviarCodigoVerificacion(usuario.getEmail(), usuario.getNombre(), usuario.getCodigoVerificacion());
    }

    // ----------------- metodos de apoyo -----------------

    private Usuario obtenerUsuario(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario con id " + id + " no existe"));
    }

    private void asignarNuevoCodigo(Usuario usuario) {
        usuario.setVerificado(false);
        usuario.setCodigoVerificacion(String.format("%06d", RANDOM.nextInt(1_000_000)));
        usuario.setCodigoExpiracion(LocalDateTime.now().plusMinutes(minutosExpiracion));
    }
}
