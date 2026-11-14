package BackEnd.DAO;

import BackEnd.DBConnection;
import BackEnd.models.whirpool;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class whirpoolDAO {
    public boolean insertar(whirpool x) {
        String sql = "INSERT INTO Whirpool (w_tiempo_inicio, w_tiempo_centrifugado, w_tiempo_reposo) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement consulta = conn.prepareStatement(sql)) {
            consulta.setDouble(1, x.getwTiempoInicio());
            consulta.setDouble(2, x.getwTiempoCentrifugado());
            consulta.setDouble(3, x.getwTiempoReposo());
            return consulta.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public List<whirpool> getAll() {
        List<whirpool> lista = new ArrayList<>();
        String sql = "SELECT * FROM Whirpool";

        try (Connection conn = DBConnection.getConnection();
                Statement consulta = conn.createStatement();
                ResultSet rs = consulta.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new whirpool(rs.getInt("w_cod"), rs.getDouble("w_tiempo_inicio"), rs.getDouble("w_tiempo_centrifugado"), rs.getDouble("w_tiempo_reposo")));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return lista;
    }

    public whirpool getPorID(int id) {
        String sql = "SELECT * FROM Whirpool WHERE w_cod = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement consulta = conn.prepareStatement(sql)) {
            consulta.setInt(1, id);
            try (ResultSet rs = consulta.executeQuery()) {
                if (rs.next()) {
                    return new whirpool(rs.getInt("w_cod"), rs.getDouble("w_tiempo_inicio"), rs.getDouble("w_tiempo_centrifugado"), rs.getDouble("w_tiempo_reposo"));
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public boolean modificar(whirpool x) {
        String sql = "UPDATE Whirpool SET w_tiempo_inicio=?, w_tiempo_centrifugado=?, w_tiempo_reposo=? WHERE w_cod=?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement consulta = conn.prepareStatement(sql)) {
            consulta.setDouble(1, x.getwTiempoInicio());
            consulta.setDouble(2, x.getwTiempoCentrifugado());
            consulta.setDouble(3, x.getwTiempoReposo());
            consulta.setInt(4, x.getwCod());
            return consulta.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }
}
