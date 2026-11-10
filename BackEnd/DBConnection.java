package BackEnd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // Si usas tu variable de entorno DB_URL, asegúrate de que tenga este mismo
    // formato.
    private static final String URL = System.getenv("DB_URL") != null ? System.getenv("DB_URL")
            : "jdbc:oracle:thin:@cerveceria_high?TNS_ADMIN=D:/Database/Wallet";

    private static final String USER = System.getenv("DB_USER");
    private static final String PASSWORD = System.getenv("DB_PASS");

    private DBConnection() {
    }

    public static Connection getConnection() throws SQLException {
        try {
            // Es buena práctica registrar explícitamente el driver en Tomcat
            Class.forName("oracle.jdbc.OracleDriver");

            // Retornamos una NUEVA conexión cada vez, eliminando la variable estática
            // global
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("No se pudo conectar a la base de datos: " + e.getMessage());
            throw e;
        } catch (ClassNotFoundException e) {
            System.err.println("Driver de Oracle no encontrado: " + e.getMessage());
            throw new SQLException("Falta el driver ojdbc en Tomcat", e);
        }
    }
}