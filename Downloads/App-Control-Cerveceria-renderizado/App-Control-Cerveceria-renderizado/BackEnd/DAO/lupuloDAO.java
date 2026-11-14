package BackEnd.DAO;

import BackEnd.DBConnection;
import BackEnd.models.lupulo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class lupuloDAO {
    public boolean insertar(lupulo x) {
        String sql = "INSERT INTO Lupulos (lu_variedad, lu_anio_cosecha, lu_formato, lu_porcentaje_alfa_acido, lu_cantidad, lu_proposito) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement consulta = conn.prepareStatement(sql)) {
            consulta.setString(1, x.getLuVariedad());
            consulta.setInt(2, x.getLuAnioCosecha());
            consulta.setString(3, x.getLuFormato());
            consulta.setDouble(4, x.getLuPorcentajeAlfaAcido());
            consulta.setDouble(5, x.getLuCantidad());
            consulta.setString(6, x.getLuProposito());
            return consulta.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public List<lupulo> getAll() {
        List<lupulo> lista = new ArrayList<>();
        String sql = "SELECT * FROM Lupulos";

        try (Connection conn = DBConnection.getConnection();
                Statement consulta = conn.createStatement();
                ResultSet rs = consulta.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new lupulo(rs.getInt("lu_cod"), rs.getString("lu_variedad"), rs.getInt("lu_anio_cosecha"), rs.getString("lu_formato"), rs.getDouble("lu_porcentaje_alfa_acido"), rs.getDouble("lu_cantidad"), rs.getString("lu_proposito")));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return lista;
    }

    public lupulo getPorID(int id) {
        String sql = "SELECT * FROM Lupulos WHERE lu_cod = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement consulta = conn.prepareStatement(sql)) {
            consulta.setInt(1, id);
            try (ResultSet rs = consulta.executeQuery()) {
                if (rs.next()) {
                    return new lupulo(rs.getInt("lu_cod"), rs.getString("lu_variedad"), rs.getInt("lu_anio_cosecha"), rs.getString("lu_formato"), rs.getDouble("lu_porcentaje_alfa_acido"), rs.getDouble("lu_cantidad"), rs.getString("lu_proposito"));
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public boolean modificar(lupulo x) {
        String sql = "UPDATE Lupulos SET lu_variedad=?, lu_anio_cosecha=?, lu_formato=?, lu_porcentaje_alfa_acido=?, lu_cantidad=?, lu_proposito=? WHERE lu_cod=?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement consulta = conn.prepareStatement(sql)) {
            consulta.setString(1, x.getLuVariedad());
            consulta.setInt(2, x.getLuAnioCosecha());
            consulta.setString(3, x.getLuFormato());
            consulta.setDouble(4, x.getLuPorcentajeAlfaAcido());
            consulta.setDouble(5, x.getLuCantidad());
            consulta.setString(6, x.getLuProposito());
            consulta.setInt(7, x.getLuCod());
            return consulta.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }
}
