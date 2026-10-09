package main.java.com.registro.tecnico.service;

import main.java.com.registro.tecnico.dto.AspiranteDTO;
import main.java.com.registro.tecnico.exception.ValidationException;
import main.java.com.registro.tecnico.model.Aspirante;
import main.java.com.registro.tecnico.repository.AspiranteRepository;
import main.java.com.registro.tecnico.service.AspiranteService;
import java.util.List;
import java.util.stream.Collectors;

public class AspiranteServiceImpl implements AspiranteService {

    private static final int EDAD_MINIMA = 18;
    private static final int EDAD_MAXIMA = 100;
    private static final String EMAIL_PATTERN = "^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$";
    private static final String TELEFONO_PATTERN = "^\\d{8,15}$";

    private final AspiranteRepository aspiranteRepository;

    public AspiranteServiceImpl(AspiranteRepository aspiranteRepository) {
        this.aspiranteRepository = aspiranteRepository;
    }

    @Override
    public List<AspiranteDTO> listar() {
        return aspiranteRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void agregar(AspiranteDTO aspiranteDTO) {
        validar(aspiranteDTO);
        verificarCorreoDisponible(aspiranteDTO.getCorreo(), 0);
        aspiranteRepository.save(toEntity(aspiranteDTO));
    }

    @Override
    public void actualizar(AspiranteDTO aspiranteDTO) {
        if (aspiranteDTO.getId() <= 0) {
            throw new ValidationException("Seleccione un aspirante de la tabla");
        }
        validar(aspiranteDTO);
        verificarCorreoDisponible(aspiranteDTO.getCorreo(), aspiranteDTO.getId());
        aspiranteRepository.update(toEntity(aspiranteDTO));
    }

    @Override
    public void eliminar(int id) {
        if (id <= 0) {
            throw new ValidationException("Seleccione un aspirante de la tabla");
        }
        aspiranteRepository.deleteById(id);
    }

    private void verificarCorreoDisponible(String correo, int excludedId) {
        if (aspiranteRepository.existsByCorreo(correo.trim(), excludedId)) {
            throw new ValidationException("Ya existe un aspirante con ese correo");
        }
    }

    private void validar(AspiranteDTO dto) {
        if (isBlank(dto.getNombre()) || isBlank(dto.getApellido()) || isBlank(dto.getCorreo())
                || isBlank(dto.getTelefono()) || isBlank(dto.getDireccion())) {
            throw new ValidationException("Complete todos los campos del aspirante");
        }
        if (!dto.getCorreo().trim().matches(EMAIL_PATTERN)) {
            throw new ValidationException("El correo no tiene un formato válido");
        }
        if (!dto.getTelefono().trim().matches(TELEFONO_PATTERN)) {
            throw new ValidationException("El teléfono debe contener solo números (8 a 15 dígitos)");
        }
        if (dto.getEdad() < EDAD_MINIMA || dto.getEdad() > EDAD_MAXIMA) {
            throw new ValidationException("La edad debe estar entre " + EDAD_MINIMA + " y " + EDAD_MAXIMA);
        }
    }

    private Aspirante toEntity(AspiranteDTO dto) {
        return new Aspirante(dto.getId(), dto.getNombre().trim(), dto.getApellido().trim(),
                dto.getCorreo().trim(), dto.getTelefono().trim(), dto.getDireccion().trim(), dto.getEdad());
    }

    private AspiranteDTO toDto(Aspirante aspirante) {
        return new AspiranteDTO(aspirante.getId(), aspirante.getNombre(), aspirante.getApellido(),
                aspirante.getCorreo(), aspirante.getTelefono(), aspirante.getDireccion(), aspirante.getEdad());
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}