package com.acme.backendunityvolunteer.infraestructure.user_management.rest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioUpdateRequest {

    @NotNull(message = "El correo no puede estar vacío")
    @Email(message = "El correo debe tener un formato válido")
    private String correo;

    @NotNull(message = "El nombre no puede estar vacío")
    @Size(min = 2, message = "El nombre debe tener al menos 2 caracteres")
    private String nombre;

    @NotNull(message = "El apellido no puede estar vacío")
    @Size(min = 2, message = "El apellido debe tener al menos 2 caracteres")
    private String apellido;

    @NotNull(message = "El teléfono no puede estar vacío")
    private String telefono;

    @NotNull(message = "El tipo de usuario no puede estar vacío")
    private String tipoUsuario;
}
