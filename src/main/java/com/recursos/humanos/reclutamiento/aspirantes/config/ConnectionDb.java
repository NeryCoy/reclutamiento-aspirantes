package main.java.com.recursos.humanos.reclutamiento.aspirantes.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import static main.java.com.recursos.humanos.reclutamiento.aspirantes.config.CredentialsDb.PASS_DB;
import static main.java.com.recursos.humanos.reclutamiento.aspirantes.config.CredentialsDb.URL_DB;
import static main.java.com.recursos.humanos.reclutamiento.aspirantes.config.CredentialsDb.USER_DB;

// Clase con patrón de diseño Singleton
public class ConnectionDb {
    private static ConnectionDb instance;
    
    // Atributo de conexión a la base de datos
    private Connection connection;

    // Constructor privado para evitar que se creen instancias desde fuera
    private ConnectionDb() {
        try {
            //asegurar el registro del driver en JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Error al cargar el Driver de MySQL: " + e.getMessage());
        }
    }
    
    // retornar la instancia de la clase
    public static synchronized ConnectionDb getInstance() {
        if (instance == null) {
            instance = new ConnectionDb();
        }
        return instance;
    }
    
    // Método público para obtener la conexión activa a la base de datos
    public Connection getConnectionDataBase() throws SQLException {
        if (connection == null || connection.isClosed()) {
            // Si la conexión es nula o está cerrada, la creamos
            connection = DriverManager.getConnection(URL_DB, USER_DB, PASS_DB);
        }
        return connection;
    }
}