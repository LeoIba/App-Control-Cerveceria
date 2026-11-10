package BackEnd.DAO;

import BackEnd.DBConnection;
import BackEnd.models.fermentacion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class fermentacionDAO {
    public boolean insertar(fermentacion x) {
        String sql = "INSERT INTO Fermentacion (f_cod) VALUES (DEFAULT)";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement consulta = conn.prepareStatement(sql)) {

            return consulta.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public List<fermentacion> getAll() {
        List<fermentacion> lista = new ArrayList<>();
        String sql = "SELECT * FROM Fermentacion";

        try (Connection conn = DBConnection.getConnection();
                Statement consulta = conn.createStatement();
                ResultSet rs = consulta.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new fermentacion(rs.getInt("f_cod")));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return lista;
    }

    public fermentacion getPorID(int id) {
        String sql = "SELECT * FROM Fermentacion WHERE f_cod = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement consulta = conn.prepareStatement(sql)) {
            consulta.setInt(1, id);
            try (ResultSet rs = consulta.executeQuery()) {
                if (rs.next()) {
                    return new fermentacion(rs.getInt("f_cod"));
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
}
