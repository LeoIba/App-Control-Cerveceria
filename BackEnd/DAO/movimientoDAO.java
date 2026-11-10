package BackEnd.DAO;

import BackEnd.DBConnection;
import BackEnd.models.movimiento;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class movimientoDAO {
    public boolean registrarMovimientoPLSQL(movimiento mov) {
        String sql = "{call movimiento_alta(?, ?, ?, ?, ?, ?)}";
        try (Connection conn = DBConnection.getConnection(); CallableStatement consulta = conn.prepareCall(sql)) {
            consulta.setDate(1, mov.getMovFecha());
            consulta.setInt(2, mov.getMovHora());
            consulta.setString(3, mov.getMovPuntoOrigen());
            consulta.setString(4, mov.getMovPuntoDestino());
            consulta.setString(5, mov.getMovResponsable());
            consulta.setInt(6, mov.getBarrilesBCod());

            consulta.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public List<movimiento> getAll() {
        List<movimiento> lista = new ArrayList<>();
        String sql = "SELECT * FROM Movimientos";

        try (Connection conn = DBConnection.getConnection();
                Statement consulta = conn.createStatement();
                ResultSet rta = consulta.executeQuery(sql)) {

            while (rta.next()) {
                lista.add(new movimiento(rta.getInt("mov_cod"), rta.getDate("mov_fecha"), rta.getInt("mov_hora"),
                        rta.getString("mov_punto_origen"), rta.getString("mov_punto_destino"),
                        rta.getString("mov_responsable"), rta.getInt("Barriles_b_cod")));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return lista;
    }

    public movimiento getPorID(int id) {
        String sql = "SELECT * FROM Movimientos WHERE mov_cod = ?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement consulta = conn.prepareStatement(sql)) {
            consulta.setInt(1, id);
            try (ResultSet rta = consulta.executeQuery()) {
                if (rta.next()) {
                    return new movimiento(rta.getInt("mov_cod"), rta.getDate("mov_fecha"), rta.getInt("mov_hora"),
                            rta.getString("mov_punto_origen"), rta.getString("mov_punto_destino"),
                            rta.getString("mov_responsable"), rta.getInt("Barriles_b_cod"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar movimiento por ID: " + e.getMessage());
        }
        return null;
    }

    public boolean modificar(movimiento movim) {
        String sql = "UPDATE Movimientos SET mov_fecha=?, mov_hora=?, mov_punto_origen=?, mov_punto_destino=?, mov_responsable=?, Barriles_b_cod=? WHERE mov_cod=?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement consulta = conn.prepareStatement(sql)) {

            consulta.setDate(1, movim.getMovFecha());
            consulta.setInt(2, movim.getMovHora());
            consulta.setString(3, movim.getMovPuntoOrigen());
            consulta.setString(4, movim.getMovPuntoDestino());
            consulta.setString(5, movim.getMovResponsable());
            consulta.setInt(6, movim.getBarrilesBCod());
            consulta.setInt(7, movim.getMovCod());
            return consulta.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

}
