package BackEnd.DAO;

import BackEnd.DBConnection;
import BackEnd.models.hervido;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class hervidoDAO {
    public boolean insertar(hervido x) {
        String sql = "INSERT INTO Hervido (h_tiempo_ebullicion) VALUES (?)";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement consulta = conn.prepareStatement(sql)) {
            consulta.setInt(1, x.gethTiempoEbullicion());
            return consulta.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public List<hervido> getAll() {
        List<hervido> lista = new ArrayList<>();
        String sql = "SELECT * FROM Hervido";

        try (Connection conn = DBConnection.getConnection();
                Statement consulta = conn.createStatement();
                ResultSet rs = consulta.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new hervido(rs.getInt("h_cod"), rs.getInt("h_tiempo_ebullicion")));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return lista;
    }

    public hervido getPorID(int id) {
        String sql = "SELECT * FROM Hervido WHERE h_cod = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement consulta = conn.prepareStatement(sql)) {
            consulta.setInt(1, id);
            try (ResultSet rs = consulta.executeQuery()) {
                if (rs.next()) {
                    return new hervido(rs.getInt("h_cod"), rs.getInt("h_tiempo_ebullicion"));
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public boolean modificar(hervido x) {
        String sql = "UPDATE Hervido SET h_tiempo_ebullicion=? WHERE h_cod=?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement consulta = conn.prepareStatement(sql)) {
            consulta.setInt(1, x.gethTiempoEbullicion());
            consulta.setInt(2, x.gethCod());
            return consulta.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }
}
