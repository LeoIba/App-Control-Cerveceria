package BackEnd.DAO;

import BackEnd.DBConnection;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

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
}
