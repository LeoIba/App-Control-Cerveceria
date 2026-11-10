package BackEnd.DAO;

import BackEnd.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import BackEnd.models.barril;

//Indirectamente, cuando cargamos el barril, lo cargamos en su lote mediante la relacion Esta_Compuesto
public class barrilDAO {
    public boolean registrarBarrilPLSQL(double capacidad, String conector, String estado, String ubicacion,
            int inventarioCod, int loteCod, Date fechaInicio) {
        String sql = "{call barril_alta(?, ?, ?, ?, ?, ?, ?)}";

        try (Connection conn = DBConnection.getConnection();
                CallableStatement consulta = conn.prepareCall(sql)) {

            consulta.setDouble(1, capacidad);
            consulta.setString(2, conector);
            consulta.setString(3, estado);
            consulta.setString(4, ubicacion);
            consulta.setInt(5, inventarioCod);
            consulta.setInt(6, loteCod);
            consulta.setDate(7, fechaInicio);

            consulta.execute();
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public List<barril> getAll() {
        List<barril> lista = new ArrayList<>();
        String sql = "SELECT * FROM Barriles";

        try (Connection conn = DBConnection.getConnection();
                Statement consulta = conn.createStatement();
                ResultSet rs = consulta.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new barril(rs.getInt("b_cod"), rs.getDouble("b_capacidad"), rs.getString("b_tipo_conector"),
                        rs.getString("b_estado_operativo"), rs.getString("b_ubicacion_actual"),
                        rs.getInt("Inventario_i_cod")));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return lista;
    }

    public barril getPorID(int id) {
        String sql = "SELECT * FROM Barriles WHERE b_cod = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement consulta = conn.prepareStatement(sql)) {
            consulta.setInt(1, id);
            try (ResultSet rta = consulta.executeQuery()) {
                if (rta.next()) {
                    return new barril(rta.getInt("b_cod"), rta.getDouble("b_capacidad"),
                            rta.getString("b_tipo_conector"), rta.getString("b_estado_operativo"),
                            rta.getString("b_ubicacion_actual"), rta.getInt("Inventario_i_cod"));
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public boolean modificar(barril barril) {
        String sql = "UPDATE Barriles SET b_capacidad=?, b_tipo_conector=?, b_estado_operativo=?, b_ubicacion_actual=?, Inventario_i_cod=? WHERE b_cod=?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement consulta = conn.prepareStatement(sql)) {

            consulta.setDouble(1, barril.getbCapacidad());
            consulta.setString(2, barril.getbTipoConector());
            consulta.setString(3, barril.getbEstadoOperativo());
            consulta.setString(4, barril.getbUbicacionActual());
            consulta.setInt(5, barril.getInventarioICod());
            consulta.setInt(6, barril.getbCod());
            return consulta.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }
}
