package BackEnd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static Connection connection = null;
    // Por defecto Oracle XE; para otra base (por ejemplo Oracle Free) definir DB_URL, p. ej.
    // jdbc:oracle:thin:@localhost:1521/freepdb1
    private static final String URL = System.getenv("DB_URL") != null ? System.getenv("DB_URL")
            : "jdbc:oracle:thin:@localhost:1521/XEPDB1";
    private static final String USER = System.getenv("DB_USER");
    private static final String PASSWORD = System.getenv("DB_PASS");

    private DBConnection() {
    }

    // Cada llamada abre una conexión nueva: los DAO la cierran con try-with-resources y, como la
    // aplicación web atiende varios pedidos a la vez, no pueden compartir una única conexión.
    public static Connection getConnection() throws SQLException {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            return connection;
        } catch (SQLException e) {
            System.err.println("No se pudo conectar a la base de datos: " + e.getMessage());
            throw e;
        }
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Cerro");
            }
        } catch (SQLException e) {
            System.err.println("FFF" + e.getMessage());
        }
    }
}
