package BackEnd.DAO;

import BackEnd.DBConnection;
import BackEnd.models.entrega;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class entregaDAO {
    public boolean registrarEntregaPLSQL(String remito, Date fEntrega, double tiempoPerm, Date fDevolucion,
            int barrilCod,
            int clienteCod) {
        String sql = "{call entrega_alta(?, ?, ?, ?, ?, ?)}";

        try (Connection conn = DBConnection.getConnection();
                CallableStatement consulta = conn.prepareCall(sql)) {
            consulta.setString(1, remito);
            consulta.setDate(2, fEntrega);
            consulta.setDouble(3, tiempoPerm);
            if (fDevolucion != null) {
                consulta.setDate(4, fDevolucion);
            } else {
                consulta.setNull(4, Types.DATE);
            }
            consulta.setInt(5, barrilCod);
            consulta.setInt(6, clienteCod);
            consulta.execute();
            return true;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    private entrega leer(ResultSet rs) throws SQLException {
        return new entrega(rs.getInt("en_cod"), rs.getString("en_remito_factura"), rs.getDate("en_fecha_entrega"),
                rs.getDouble("en_tiempo_permanencia"), rs.getDate("en_fecha_devolucion"), rs.getInt("Barriles_b_cod"),
                rs.getInt("Clientes_cl_cod"));
    }

    public List<entrega> getAll() {
        List<entrega> lista = new ArrayList<>();
        String sql = "SELECT * FROM Entregas";

        try (Connection conn = DBConnection.getConnection();
                Statement consulta = conn.createStatement();
                ResultSet rs = consulta.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(leer(rs));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return lista;
    }

    public entrega getPorID(int id) {
        String sql = "SELECT * FROM Entregas WHERE en_cod = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement consulta = conn.prepareStatement(sql)) {
            consulta.setInt(1, id);
            try (ResultSet rs = consulta.executeQuery()) {
                if (rs.next()) {
                    return leer(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public boolean modificar(entrega en) {
        String sql = "UPDATE Entregas SET en_remito_factura=?, en_fecha_entrega=?, en_tiempo_permanencia=?, en_fecha_devolucion=?, Barriles_b_cod=?, Clientes_cl_cod=? WHERE en_cod=?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement consulta = conn.prepareStatement(sql)) {
            consulta.setString(1, en.getEnRemitoFactura());
            consulta.setDate(2, en.getEnFechaEntrega());
            consulta.setDouble(3, en.getEnTiempoPermanencia());
            if (en.getEnFechaDevolucion() != null)
                consulta.setDate(4, en.getEnFechaDevolucion());
            else
                consulta.setNull(4, Types.DATE);
            consulta.setInt(5, en.getBarrilesBCod());
            consulta.setInt(6, en.getClientesClCod());
            consulta.setInt(7, en.getEnCod());
            return consulta.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }
}
