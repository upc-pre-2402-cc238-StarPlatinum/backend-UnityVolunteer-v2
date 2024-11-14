package com.acme.backendunityvolunteer.application.user_management.dto.user_services;

import com.acme.backendunityvolunteer.application.user_management.dto.UsuarioDTO;
import com.acme.backendunityvolunteer.domain.user_management.model.TipoUsuario;
import com.acme.backendunityvolunteer.domain.user_management.model.Usuario;
import com.acme.backendunityvolunteer.domain.user_management.model.repository.UsuarioRepository;
import com.acme.backendunityvolunteer.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioDTO registrarUsuario(UsuarioDTO usuarioDTO) {
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setCorreo(usuarioDTO.getCorreo());
        nuevoUsuario.setNombre(usuarioDTO.getNombre());
        nuevoUsuario.setApellido(usuarioDTO.getApellido());
        nuevoUsuario.setTelefono(usuarioDTO.getTelefono());
        nuevoUsuario.setContrasenia(usuarioDTO.getContrasenia());

        try {
            nuevoUsuario.setTipoUsuario(TipoUsuario.valueOf(usuarioDTO.getTipoUsuario().toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Tipo de usuario inválido.");
        }

        Usuario usuarioGuardado = usuarioRepository.save(nuevoUsuario);
        return mapToDTO(usuarioGuardado);
    }


    public UsuarioDTO obtenerUsuarioPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        return mapToDTO(usuario);
    }

    // Actualizar un usuario
    public void actualizarUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuarioExistente = usuarioRepository.findById(usuarioDTO.getId())
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        usuarioExistente.setCorreo(usuarioDTO.getCorreo());
        usuarioExistente.setNombre(usuarioDTO.getNombre());
        usuarioExistente.setApellido(usuarioDTO.getApellido());
        usuarioExistente.setTelefono(usuarioDTO.getTelefono());
        usuarioExistente.setTipoUsuario(TipoUsuario.valueOf(usuarioDTO.getTipoUsuario().toUpperCase()));

        usuarioRepository.save(usuarioExistente);
    }

    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    // Iniciar sesión con correo y contraseña
    public String iniciarSesion(String correo, String contrasenia) {
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con ese correo"));

        if (!usuario.getContrasenia().equals(contrasenia)) {
            throw new IllegalArgumentException("Contraseña incorrecta");
        }

        // Retornar un token ficticio o real generado
        return "token_generado";
    }


    public UsuarioDTO obtenerUsuarioPorCorreo(String correo) {
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado con ese correo"));

        return mapToDTO(usuario);
    }

    // Conversión de entidad a DTO
    private UsuarioDTO mapToDTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setCorreo(usuario.getCorreo());
        dto.setNombre(usuario.getNombre());
        dto.setApellido(usuario.getApellido());
        dto.setTelefono(usuario.getTelefono());
        dto.setTipoUsuario(usuario.getTipoUsuario().name());
        return dto;
    }
}