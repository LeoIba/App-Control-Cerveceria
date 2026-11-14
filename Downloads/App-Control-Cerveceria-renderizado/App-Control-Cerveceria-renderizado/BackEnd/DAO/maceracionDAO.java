package BackEnd.DAO;

import BackEnd.DBConnection;
import BackEnd.models.maceracion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class maceracionDAO {
    public boolean insertar(maceracion x) {
        String sql = "INSERT INTO Maceracion (m_relacion_agua_grano) VALUES (?)";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement consulta = conn.prepareStatement(sql)) {
            consulta.setDouble(1, x.getmRelacionAguaGrano());
            return consulta.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public List<maceracion> getAll() {
        List<maceracion> lista = new ArrayList<>();
        String sql = "SELECT * FROM Maceracion";

        try (Connection conn = DBConnection.getConnection();
                Statement consulta = conn.createStatement();
                ResultSet rs = consulta.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new maceracion(rs.getInt("m_cod"), rs.getDouble("m_relacion_agua_grano")));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return lista;
    }

    public maceracion getPorID(int id) {
        String sql = "SELECT * FROM Maceracion WHERE m_cod = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement consulta = conn.prepareStatement(sql)) {
            consulta.setInt(1, id);
            try (ResultSet rs = consulta.executeQuery()) {
                if (rs.next()) {
                    return new maceracion(rs.getInt("m_cod"), rs.getDouble("m_relacion_agua_grano"));
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public boolean modificar(maceracion x) {
        String sql = "UPDATE Maceracion SET m_relacion_agua_grano=? WHERE m_cod=?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement consulta = conn.prepareStatement(sql)) {
            consulta.setDouble(1, x.getmRelacionAguaGrano());
            consulta.setInt(2, x.getmCod());
            return consulta.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }
}
