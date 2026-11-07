package BackEnd.DAO;

import BackEnd.DBConnection;
import BackEnd.models.inventario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class inventarioDAO {
    public boolean registrarInventarioPLSQL(double litrosTotales, String estadoEtapa, int barrilCod) {
        String sql = "{call inventario_alta(?, ?, ?)}";
        try (Connection conn = DBConnection.getConnection(); CallableStatement consulta = conn.prepareCall(sql)) {
            consulta.setDouble(1, litrosTotales);
            consulta.setString(2, estadoEtapa);
            consulta.setInt(3, barrilCod);
            consulta.execute();
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public List<inventario> getAll() {
        List<inventario> retorno = new ArrayList<>();
        String sql = "SELECT i_cod, i_litros_totales, i_estado_etapa FROM Inventario";
        try (Connection conn = DBConnection.getConnection();
                Statement consulta = conn.createStatement();
                ResultSet rta = consulta.executeQuery(sql)) {
            while (rta.next()) {
                retorno.add(new inventario(rta.getInt("i_cod"), rta.getDouble("i_litros_totales"),
                        rta.getString("i_estado_etapa")));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return retorno;
    }

    public inventario getporID(int id) {
        String sql = "SELECT i_cod, i_litros_totales, i_estado_etapa FROM Inventario WHERE i_cod = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement consulta = conn.prepareStatement(sql)) {

            consulta.setInt(1, id);
            try (ResultSet rta = consulta.executeQuery()) {
                if (rta.next()) {
                    return new inventario(rta.getInt("i_cod"), rta.getDouble("i_litros_totales"),
                            rta.getString("i_estado_etapa"));
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public boolean modificar(inventario inventario) {
        String sql = "UPDATE Inventario SET i_litros_totales = ?, i_estado_etapa = ? WHERE i_cod = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement consultaUpdate = conn.prepareStatement(sql)) {

            consultaUpdate.setDouble(1, inventario.getiLitrosTotales());
            consultaUpdate.setString(2, inventario.getiEstadoEtapa());
            consultaUpdate.setInt(3, inventario.getiCod());

            return consultaUpdate.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }
}
