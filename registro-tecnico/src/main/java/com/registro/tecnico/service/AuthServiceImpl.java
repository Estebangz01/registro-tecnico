package main.java.com.registro.tecnico.service;

import main.java.com.registro.tecnico.dto.LoginDTO;
import main.java.com.registro.tecnico.dto.RegistroUsuarioDTO;
import main.java.com.registro.tecnico.dto.RolDTO;
import main.java.com.registro.tecnico.dto.UsuarioDTO;
import main.java.com.registro.tecnico.exception.AuthException;
import main.java.com.registro.tecnico.exception.ValidationException;
import main.java.com.registro.tecnico.model.Rol;
import main.java.com.registro.tecnico.model.Usuario;
import main.java.com.registro.tecnico.repository.RolRepository;
import main.java.com.registro.tecnico.repository.UsuarioRepository;
import main.java.com.registro.tecnico.service.AuthService;
import java.util.List;
import java.util.stream.Collectors;
import org.mindrot.jbcrypt.BCrypt;

public class AuthServiceImpl implements AuthService {

    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final String EMAIL_PATTERN = "^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$";

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;

    public AuthServiceImpl(UsuarioRepository usuarioRepository, RolRepository rolRepository) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
    }

    @Override
    public UsuarioDTO login(LoginDTO loginDTO) {
        if (isBlank(loginDTO.getNombreUsuario()) || isBlank(loginDTO.getPassword())) {
            throw new ValidationException("Ingrese su usuario y contraseña");
        }
        Usuario usuario = usuarioRepository.findByNombreUsuario(loginDTO.getNombreUsuario().trim())
                .orElseThrow(() -> new AuthException("Usuario o contraseña incorrectos"));
        if (!BCrypt.checkpw(loginDTO.getPassword(), usuario.getPasswordHash())) {
            throw new AuthException("Usuario o contraseña incorrectos");
        }
        return new UsuarioDTO(usuario.getId(), usuario.getNombreUsuario(),
                usuario.getCorreo(), usuario.getRol().getNombre());
    }

    @Override
    public void registrar(RegistroUsuarioDTO registroDTO) {
        validarRegistro(registroDTO);
        String nombreUsuario = registroDTO.getNombreUsuario().trim();
        String correo = registroDTO.getCorreo().trim();
        if (usuarioRepository.existsByNombreUsuarioOrCorreo(nombreUsuario, correo)) {
            throw new ValidationException("El usuario o el correo ya están registrados");
        }
        Rol rol = rolRepository.findById(registroDTO.getIdRol())
                .orElseThrow(() -> new ValidationException("El rol seleccionado no existe"));
        String hash = BCrypt.hashpw(registroDTO.getPassword(), BCrypt.gensalt());
        usuarioRepository.save(new Usuario(0, nombreUsuario, correo, hash, rol));
    }

    @Override
    public List<RolDTO> listarRoles() {
        return rolRepository.findAll().stream()
                .map(rol -> new RolDTO(rol.getId(), rol.getNombre()))
                .collect(Collectors.toList());
    }

    private void validarRegistro(RegistroUsuarioDTO registroDTO) {
        if (isBlank(registroDTO.getNombreUsuario()) || isBlank(registroDTO.getCorreo())
                || isBlank(registroDTO.getPassword()) || isBlank(registroDTO.getConfirmacionPassword())) {
            throw new ValidationException("Complete todos los campos");
        }
        if (registroDTO.getIdRol() <= 0) {
            throw new ValidationException("Seleccione un rol");
        }
        if (!registroDTO.getCorreo().trim().matches(EMAIL_PATTERN)) {
            throw new ValidationException("El correo no tiene un formato válido");
        }
        if (registroDTO.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new ValidationException("La contraseña debe tener al menos " + MIN_PASSWORD_LENGTH + " caracteres");
        }
        if (!registroDTO.getPassword().equals(registroDTO.getConfirmacionPassword())) {
            throw new ValidationException("Las contraseñas no coinciden");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}