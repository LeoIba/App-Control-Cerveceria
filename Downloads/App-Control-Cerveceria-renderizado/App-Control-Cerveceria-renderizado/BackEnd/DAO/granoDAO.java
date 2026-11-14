package BackEnd.DAO;

import BackEnd.DBConnection;
import BackEnd.models.grano;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class granoDAO {
    public boolean insertar(grano x) {
        String sql = "INSERT INTO Granos (g_nombre, g_tipo, g_porcentaje, g_peso, g_potencial_extraccion, g_color) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement consulta = conn.prepareStatement(sql)) {
            consulta.setString(1, x.getgNombre());
            consulta.setString(2, x.getgTipo());
            consulta.setDouble(3, x.getgPorcentaje());
            consulta.setDouble(4, x.getgPeso());
            consulta.setString(5, x.getgPotencialExtraccion());
            consulta.setString(6, x.getgColor());
            return consulta.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public List<grano> getAll() {
        List<grano> lista = new ArrayList<>();
        String sql = "SELECT * FROM Granos";

        try (Connection conn = DBConnection.getConnection();
                Statement consulta = conn.createStatement();
                ResultSet rs = consulta.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new grano(rs.getInt("g_cod"), rs.getString("g_nombre"), rs.getString("g_tipo"), rs.getDouble("g_porcentaje"), rs.getDouble("g_peso"), rs.getString("g_potencial_extraccion"), rs.getString("g_color")));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return lista;
    }

    public grano getPorID(int id) {
        String sql = "SELECT * FROM Granos WHERE g_cod = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement consulta = conn.prepareStatement(sql)) {
            consulta.setInt(1, id);
            try (ResultSet rs = consulta.executeQuery()) {
                if (rs.next()) {
                    return new grano(rs.getInt("g_cod"), rs.getString("g_nombre"), rs.getString("g_tipo"), rs.getDouble("g_porcentaje"), rs.getDouble("g_peso"), rs.getString("g_potencial_extraccion"), rs.getString("g_color"));
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public boolean modificar(grano x) {
        String sql = "UPDATE Granos SET g_nombre=?, g_tipo=?, g_porcentaje=?, g_peso=?, g_potencial_extraccion=?, g_color=? WHERE g_cod=?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement consulta = conn.prepareStatement(sql)) {
            consulta.setString(1, x.getgNombre());
            consulta.setString(2, x.getgTipo());
            consulta.setDouble(3, x.getgPorcentaje());
            consulta.setDouble(4, x.getgPeso());
            consulta.setString(5, x.getgPotencialExtraccion());
            consulta.setString(6, x.getgColor());
            consulta.setInt(7, x.getgCod());
            return consulta.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }
}
