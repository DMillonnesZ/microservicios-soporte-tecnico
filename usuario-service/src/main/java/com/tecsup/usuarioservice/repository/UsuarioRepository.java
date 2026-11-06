package com.tecsup.usuarioservice.repository;

import com.tecsup.usuarioservice.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Para validar email unico al registrar
    boolean existsByEmail(String email);

    // Para validar email unico al actualizar (excluyendo al mismo usuario)
    boolean existsByEmailAndIdNot(String email, Long id);
}
