package main.java.com.recursos.humanos.reclutamiento.aspirantes.repository;

import main.java.com.recursos.humanos.reclutamiento.aspirantes.config.ConnectionDb;
import main.java.com.recursos.humanos.reclutamiento.aspirantes.security.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {
    private static UserRepository instance;

    private UserRepository() {}

    public static synchronized UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    //registrar usuario en la base de datos
    public boolean registrarUsuario(String idUsuario, String idRol, String correo, String passwordPlana) {
        String hashedPassword = BCrypt.hashpw(passwordPlana, BCrypt.gensalt());
        String sql = "INSERT INTO usuarios (id_usuario, id_rol, correo, contrasena) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectionDb.getInstance().getConnectionDataBase(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, idUsuario);
            pstmt.setString(2, idRol);
            pstmt.setString(3, correo);
            pstmt.setString(4, hashedPassword);

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al registrar usuario: " + e.getMessage());
            return false;
        }
    }

    //validar credenciales de inicio de sesión y retornar el rol si es exitoso
    public String autenticarYObtenerRol(String correo, String passwordPlana) {
        //traer el nombre del rol
        String sql = "SELECT u.contrasena, r.nombre FROM usuarios u " +
                     "JOIN roles r ON u.id_rol = r.id_rol WHERE u.correo = ?";

        try (Connection conn = ConnectionDb.getInstance().getConnectionDataBase();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, correo);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("contrasena");
                String rolNombre = rs.getString("nombre");

                //verificar si la contraseña coincide con el hash almacenado
                if (BCrypt.checkpw(passwordPlana, storedHash)) {
                    return rolNombre; // Retorna el rol
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en la autenticación: " + e.getMessage());
        }
        return null; // Retorna null si las credenciales son incorrectas
    }
}