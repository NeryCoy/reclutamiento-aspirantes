package main.java.com.recursos.humanos.reclutamiento.aspirantes.service;

import main.java.com.recursos.humanos.reclutamiento.aspirantes.exception.BusinessException;
import main.java.com.recursos.humanos.reclutamiento.aspirantes.repository.UserRepository;

public class UserService {
    private static UserService instance;
    private final UserRepository userRepository = UserRepository.getInstance();

    private UserService() {}

    public static synchronized UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    // Lógica de negocio para el registro de nuevos usuarios
    public boolean registrar(String idUsuario, String nombre, String correo, String password, String confirmarPassword, int rolId) {
    if (idUsuario == null || idUsuario.trim().isEmpty() ||
        correo == null || correo.trim().isEmpty() ||
        password == null || password.isEmpty() ||
        confirmarPassword == null || confirmarPassword.isEmpty()) {
        throw new BusinessException("Todos los campos obligatorios deben ser llenados.");
    }

    if (!password.equals(confirmarPassword)) {
        throw new BusinessException("Las contraseñas no coinciden.");
    }

    if (password.length() < 4) {
        throw new BusinessException("La contraseña es muy corta.");
    }

    //mapear el rol ("ROL-001" o "ROL-002")
    String idRol = (rolId == 2) ? "ROL-002" : "ROL-001";

    boolean registrado = userRepository.registrarUsuario(idUsuario, idRol, correo, password);
    
    if (!registrado) {
        throw new BusinessException("No se pudo registrar el usuario. Verifique si el ID o correo ya existen.");
    }
    return true;
}

    //logica para iniciar sesión
    public String iniciarSesion(String correo, String password) {
        if (correo == null || correo.trim().isEmpty() || password == null || password.isEmpty()) {
            throw new BusinessException("Debe ingresar el correo y la contraseña.");
        }

        String rol = userRepository.autenticarYObtenerRol(correo, password);
        if (rol == null) {
            throw new BusinessException("Correo o contraseña incorrectos.");
        }

        return rol; // Retorna el rol
    }
}