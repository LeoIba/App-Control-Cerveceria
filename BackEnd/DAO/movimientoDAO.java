package BackEnd.DAO;

import BackEnd.DBConnection;
import BackEnd.models.movimiento;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class movimientoDAO {
    public boolean registrarMovimientoPLSQL(movimiento mov) {
        String sql = "{call movimiento_alta(?, ?, ?, ?, ?, ?, ?)";
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
}
