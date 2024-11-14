package com.acme.backendunityvolunteer.application.user_management.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDTO {
    private Long id;

    @Email(message = "Correo debe tener un formato válido")
    @NotNull(message = "El correo es obligatorio")
    private String correo;

    @NotNull(message = "El nombre es obligatorio")
    private String nombre;

    @NotNull(message = "El apellido es obligatorio")
    private String apellido;

    @Size(min = 8, message = "El teléfono debe tener al menos 8 caracteres")
    private String telefono;

    @NotNull(message = "El tipo de usuario es obligatorio")
    private String tipoUsuario;

    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String contrasenia;  // Asegúrate de gestionar la contraseña de forma segura
}
