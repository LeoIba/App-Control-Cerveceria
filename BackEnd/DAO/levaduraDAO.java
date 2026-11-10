package BackEnd.DAO;

import BackEnd.DBConnection;
import BackEnd.models.levadura;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class levaduraDAO {
    public boolean insertar(levadura x) {
        String sql = "INSERT INTO Levaduras (le_cepa, le_formato_lev, le_cantidad_lev, le_tasa_inoculacion) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement consulta = conn.prepareStatement(sql)) {
            consulta.setString(1, x.getLeCepa());
            consulta.setString(2, x.getLeFormatoLev());
            consulta.setDouble(3, x.getLeCantidadLev());
            consulta.setDouble(4, x.getLeTasaInoculacion());
            return consulta.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public List<levadura> getAll() {
        List<levadura> lista = new ArrayList<>();
        String sql = "SELECT * FROM Levaduras";

        try (Connection conn = DBConnection.getConnection();
                Statement consulta = conn.createStatement();
                ResultSet rs = consulta.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new levadura(rs.getInt("le_cod"), rs.getString("le_cepa"), rs.getString("le_formato_lev"), rs.getDouble("le_cantidad_lev"), rs.getDouble("le_tasa_inoculacion")));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return lista;
    }

    public levadura getPorID(int id) {
        String sql = "SELECT * FROM Levaduras WHERE le_cod = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement consulta = conn.prepareStatement(sql)) {
            consulta.setInt(1, id);
            try (ResultSet rs = consulta.executeQuery()) {
                if (rs.next()) {
                    return new levadura(rs.getInt("le_cod"), rs.getString("le_cepa"), rs.getString("le_formato_lev"), rs.getDouble("le_cantidad_lev"), rs.getDouble("le_tasa_inoculacion"));
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public boolean modificar(levadura x) {
        String sql = "UPDATE Levaduras SET le_cepa=?, le_formato_lev=?, le_cantidad_lev=?, le_tasa_inoculacion=? WHERE le_cod=?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement consulta = conn.prepareStatement(sql)) {
            consulta.setString(1, x.getLeCepa());
            consulta.setString(2, x.getLeFormatoLev());
            consulta.setDouble(3, x.getLeCantidadLev());
            consulta.setDouble(4, x.getLeTasaInoculacion());
            consulta.setInt(5, x.getLeCod());
            return consulta.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }
}
