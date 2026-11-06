package com.tecsup.usuarioservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** JSON para verificar el email: { "codigo": "123456" } */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerificacionRequestDTO {

    @NotBlank(message = "El codigo es obligatorio")
    @Pattern(regexp = "^\\d{6}$", message = "El codigo debe tener 6 digitos")
    private String codigo;
}
