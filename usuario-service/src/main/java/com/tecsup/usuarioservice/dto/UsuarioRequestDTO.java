package com.tecsup.usuarioservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de ENTRADA: lo que el cliente envia en el JSON (POST / PUT).
 * No incluye id ni fechaRegistro: esos los genera el sistema.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    private String nombre;

    @NotBlank(message = "El email es obligatorio")
    @Email(regexp = "^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)*\\.[a-zA-Z]{2,}$",
           message = "El email no tiene un formato valido")
    @Size(max = 120, message = "El email no debe superar 120 caracteres")
    private String email;

    @Pattern(regexp = "^9\\d{8}$", message = "El telefono debe tener 9 digitos y empezar con 9")
    private String telefono;

    @NotBlank(message = "El area es obligatoria")
    @Size(max = 60, message = "El area no debe superar 60 caracteres")
    private String area;
}
