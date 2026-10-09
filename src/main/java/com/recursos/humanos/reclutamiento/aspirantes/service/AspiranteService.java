package main.java.com.recursos.humanos.reclutamiento.aspirantes.service;

import main.java.com.recursos.humanos.reclutamiento.aspirantes.exception.BusinessException;
import main.java.com.recursos.humanos.reclutamiento.aspirantes.model.AspiranteDTO;
import main.java.com.recursos.humanos.reclutamiento.aspirantes.repository.AspiranteRepository;
import javafx.collections.ObservableList;

public class AspiranteService {
    private static AspiranteService instance;
    private final AspiranteRepository repository = AspiranteRepository.getInstance();

    private AspiranteService() {}

    public static synchronized AspiranteService getInstance() {
        if (instance == null) instance = new AspiranteService();
        return instance;
    }

    public boolean registrarAspirante(String idAspirantes, String idPais, String idUsuario, String nombre, String apellido, String telefono, String edadStr) {
        if (idAspirantes.isEmpty() || idPais.isEmpty() || idUsuario.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || telefono.isEmpty() || edadStr.isEmpty()) {
            throw new BusinessException("Todos los campos son obligatorios.");
        }
        try {
            int edad = Integer.parseInt(edadStr);
            if (edad < 18) {
                throw new BusinessException("El aspirante debe ser mayor de edad.");
            }

            AspiranteDTO dto = new AspiranteDTO(idAspirantes, idPais, idUsuario, nombre, apellido, telefono, edad);
            return repository.guardar(dto);
        } catch (NumberFormatException e) {
            throw new BusinessException("La edad debe ser un número válido.");
        }
    }

    public ObservableList<AspiranteDTO> obtenerAspirantes() {
        return repository.obtenerTodos();
    }

    public boolean actualizarAspirante(String idAspirantes, String idPais, String idUsuario, String nombre, String apellido, String telefono, int edad) {
        if (idAspirantes.isEmpty() || nombre.isEmpty() || apellido.isEmpty()) {
            throw new BusinessException("Los datos principales para actualizar son obligatorios.");
        }
        AspiranteDTO dto = new AspiranteDTO(idAspirantes, idPais, idUsuario, nombre, apellido, telefono, edad);
        return repository.actualizar(dto);
    }

    public boolean eliminarAspirante(String idAspirantes) {
        if (idAspirantes == null || idAspirantes.isEmpty()) {
            throw new BusinessException("Debe seleccionar un aspirante válido para eliminar.");
        }
        return repository.eliminar(idAspirantes);
    }
}