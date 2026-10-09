package main.java.com.recursos.humanos.reclutamiento.aspirantes.repository;

import main.java.com.recursos.humanos.reclutamiento.aspirantes.config.ConnectionDb;
import main.java.com.recursos.humanos.reclutamiento.aspirantes.model.AspiranteDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

public class AspiranteRepository {
    private static AspiranteRepository instance;

    private AspiranteRepository() {}

    public static synchronized AspiranteRepository getInstance() {
        if (instance == null) instance = new AspiranteRepository();
        return instance;
    }

    public boolean guardar(AspiranteDTO asp) {
        String sql = "INSERT INTO aspirantes (id_aspirantes, id_pais, id_usuario, nombre, apellido, telefono, edad) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionDb.getInstance().getConnectionDataBase();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, asp.getIdAspirantes());
            pstmt.setString(2, asp.getIdPais());
            pstmt.setString(3, asp.getIdUsuario());
            pstmt.setString(4, asp.getNombre());
            pstmt.setString(5, asp.getApellido());
            pstmt.setString(6, asp.getTelefono());
            pstmt.setInt(7, asp.getEdad());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al guardar aspirante: " + e.getMessage());
            return false;
        }
    }

    public ObservableList<AspiranteDTO> obtenerTodos() {
        ObservableList<AspiranteDTO> lista = FXCollections.observableArrayList();
        String sql = "SELECT * FROM aspirantes";
        try (Connection conn = ConnectionDb.getInstance().getConnectionDataBase();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new AspiranteDTO(
                    rs.getString("id_aspirantes"),
                    rs.getString("id_pais"),
                    rs.getString("id_usuario"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("telefono"),
                    rs.getInt("edad")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar aspirantes: " + e.getMessage());
        }
        return lista;
    }

    public boolean actualizar(AspiranteDTO asp) {
        String sql = "UPDATE aspirantes SET id_pais=?, id_usuario=?, nombre=?, apellido=?, telefono=?, edad=? WHERE id_aspirantes=?";
        try (Connection conn = ConnectionDb.getInstance().getConnectionDataBase();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, asp.getIdPais());
            pstmt.setString(2, asp.getIdUsuario());
            pstmt.setString(3, asp.getNombre());
            pstmt.setString(4, asp.getApellido());
            pstmt.setString(5, asp.getTelefono());
            pstmt.setInt(6, asp.getEdad());
            pstmt.setString(7, asp.getIdAspirantes());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(String idAspirantes) {
        String sql = "DELETE FROM aspirantes WHERE id_aspirantes=?";
        try (Connection conn = ConnectionDb.getInstance().getConnectionDataBase();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, idAspirantes);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar: " + e.getMessage());
            return false;
        }
    }
}