package BackEnd.DAO;

import BackEnd.DBConnection;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Types;

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
}
